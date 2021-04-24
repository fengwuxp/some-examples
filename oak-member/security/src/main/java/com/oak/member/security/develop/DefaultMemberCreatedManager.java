package com.oak.member.security.develop;

import com.oak.member.logic.MemberCreatedException;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.MemberCreateOnSuccessHandler;
import com.oak.member.logic.develop.MemberCreatedManager;
import com.oak.member.logic.develop.MemberCreatedProvider;
import com.oak.member.logic.develop.model.MemberRegisterModel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wxup
 */
@Slf4j
@Setter
public class DefaultMemberCreatedManager implements MemberCreatedManager, InitializingBean, ApplicationContextAware {


    private List<MemberCreatedProvider> memberCreatedProviders;

    private ApplicationContext applicationContext;

    protected List<MemberCreateOnSuccessHandler> memberCreateProcessors;

    /**
     * 遍历所有的 {@link MemberCreatedProvider} 直到用户创建成功
     *
     * @param model 用于用户注册的信息
     * @return 创建成的用户信息
     * @throws MemberCreatedException 发生异常将回滚事务
     */
    @Override
    @Transactional(rollbackFor = {MemberCreatedException.class, RuntimeException.class})
    public MemberDefinition create(MemberRegisterModel model) throws MemberCreatedException {
        for (MemberCreatedProvider memberCreatedProvider : getMemberCreatedProviders()) {
            if (memberCreatedProvider.supports(model.getClass())) {
                MemberDefinition memberDefinition = memberCreatedProvider.create(model);
                this.onSuccessful(memberDefinition, model);
                return memberDefinition;
            }
        }
        throw new MemberCreatedException("不支持的注册方式: " + model.getClass());
    }

    protected void onSuccessful(MemberDefinition member, MemberRegisterModel model) {
        if (memberCreateProcessors != null) {
            for (MemberCreateOnSuccessHandler memberCreateProcessor : memberCreateProcessors) {
                try {
                    if (memberCreateProcessor.supports(model.getClass())) {
                        memberCreateProcessor.onCreatedSuccess(member, model);
                    }
                } catch (Exception e) {
                    log.error("处理创建用户onCreatedSuccess异常", e);
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (memberCreatedProviders == null) {
            try {
                Map<String, MemberCreatedProvider> memberCreatedProviderMap = this.applicationContext.getBeansOfType(MemberCreatedProvider.class);
                this.memberCreatedProviders = new ArrayList<>(memberCreatedProviderMap.values());
            } catch (BeansException e) {
                log.error("初始化MemberCreatedProvider失败", e);
                this.memberCreatedProviders = Collections.emptyList();
            }
        }
        try {
            Map<String, MemberCreateOnSuccessHandler> memberCreateProcessorMap = this.applicationContext.getBeansOfType(MemberCreateOnSuccessHandler.class);
            Collection<MemberCreateOnSuccessHandler> values = memberCreateProcessorMap.values();
            ArrayList<MemberCreateOnSuccessHandler> memberCreateProcessors = new ArrayList<>(values.size());
            memberCreateProcessors.addAll(values);
            memberCreateProcessors.sort(Comparator.comparingInt(MemberCreateOnSuccessHandler::getOrder));
            this.memberCreateProcessors = memberCreateProcessors;
        } catch (BeansException e) {
            log.warn("初始化MemberCreateProcessor失败", e);
        }
    }


    public List<MemberCreatedProvider> getMemberCreatedProviders() {
        return memberCreatedProviders;
    }
}
