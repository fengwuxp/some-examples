package com.oak.member.security.develop;

import com.oak.member.logic.MemberCreatedException;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.bind.MemberBinder;
import com.oak.member.logic.develop.model.MemberRegisterModel;
import com.oak.member.security.bind.req.BindOpenReq;
import com.oak.member.security.develop.req.OpenIdRegisterReq;
import com.oak.member.services.member.req.CreateMemberReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


/**
 * 通过openId注册户注册的实现
 *
 * @author wxup
 */
@Slf4j
public class OpenIdMemberCreatedProvider extends AbstractMemberCreateProvider {


    @Autowired
    private MemberBinder memberBinder;


    @Override
    public MemberDefinition create(MemberRegisterModel model) throws MemberCreatedException {
        OpenIdRegisterReq req = (OpenIdRegisterReq) model;

        /**
         * 检查用户状态
         * 1:通过openId和手机号码等查看用户是否存在
         * 2：如果用户未存在则创建
         * 3：如果openId存在用户 手机号码不存在用户，则进行绑定，反之亦然
         * 4：如果openId和手机都存在用户且用户不同，则抛异常
         */
        Long openMemberId = this.memberLoader.checkByOpenId(req.getOpenType(), req.getOpenId(), req.getUnionId());
        String mobilePhone = req.getMobilePhone();
        Long memberId = memberLoader.checkByMobilePhone(mobilePhone);
        boolean needCreated = openMemberId == null && memberId == null;
        if (needCreated) {
            MemberDefinition memberDefinition = super.create(model);
            // 绑定开放平台
            BindOpenReq openReq = new BindOpenReq();
            openReq.setMemberId(memberDefinition.getId())
                    .setOpenType(req.getOpenType())
                    .setOpenId(req.getOpenId())
                    .setUnionId(req.getUnionId())
                    .setSubscribe(req.getSubscribe())
                    .setBindChannelCode(req.getChannelCode());
            memberBinder.bindOpen(openReq);
            return memberDefinition;
        }
        boolean needBindOpen = openMemberId == null && memberId != null;
        if (needBindOpen) {
            // 需要绑定开放平台
            BindOpenReq openReq = new BindOpenReq();
            openReq.setMemberId(memberId)
                    .setOpenType(req.getOpenType())
                    .setOpenId(req.getOpenId())
                    .setUnionId(req.getUnionId())
                    .setSubscribe(req.getSubscribe())
                    .setBindChannelCode(req.getChannelCode());
            memberBinder.bindOpen(openReq);
            return memberService.findById(memberId);
        }
        boolean needBindMobilePhone = openMemberId != null && memberId == null;
        if (needBindMobilePhone && StringUtils.hasText(mobilePhone)) {
            // 需要绑定手机
            memberBinder.bindMobilePhone(openMemberId, mobilePhone);
            return memberService.findById(openMemberId);
        }

        if (openMemberId.equals(memberId)) {
            return memberService.findById(openMemberId);
        } else {
            throw new MemberCreatedException("该手机号码已经被其他账号绑定");
        }

    }


    @Override
    protected void handleCreateMemberReq(CreateMemberReq memberReq, MemberRegisterModel model) {
        OpenIdRegisterReq req = (OpenIdRegisterReq) model;
        memberReq.setMobilePhone(req.getMobilePhone())
                .setEmail(req.getEmail());
    }



    @Override
    public boolean supports(Class<?> model) {
        return OpenIdRegisterReq.class.isAssignableFrom(model);
    }
}

