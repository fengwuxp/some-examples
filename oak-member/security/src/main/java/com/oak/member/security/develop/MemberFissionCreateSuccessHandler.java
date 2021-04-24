package com.oak.member.security.develop;

import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.MemberCreateOnSuccessHandler;
import com.oak.member.logic.develop.model.MemberFissionModel;
import com.oak.member.logic.develop.model.MemberRegisterModel;
import com.oak.member.logic.enums.PromotionType;
import com.oak.member.security.develop.req.MobileRegisterReq;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.memberagent.MemberAgentService;
import com.oak.member.services.memberagent.info.MemberAgentInfo;
import com.oak.member.services.memberagent.req.CreateMemberAgentReq;
import com.oak.member.services.promotion.MemberPromotionService;
import com.oak.member.services.promotion.req.CreateMemberPromotionReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 * 用户裂变（推广）处理
 * 用于创建用户的推荐发展关系
 *
 * @author wxup
 */
@Slf4j
public class MemberFissionCreateSuccessHandler implements MemberCreateOnSuccessHandler {


    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAgentService memberAgentService;

    @Autowired
    private MemberPromotionService memberPromotionService;

    @Override
    public void onCreatedSuccess(MemberDefinition member, MemberRegisterModel model) {

        if (!MemberFissionModel.class.isAssignableFrom(model.getClass())) {
            return;
        }
        this.createDevelopRelationship((MemberFissionModel) model, member);
        this.createMemberAgent(member, model);
        // 更新上级代理的发展用户数量
        this.updateReferrerInfo(member, model);
    }

    /**
     * {@link #}
     *
     * @param model
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>MemberRegisterModel</code> class presented
     */
    @Override
    public boolean supports(Class<?> model) {
        return MobileRegisterReq.class.isAssignableFrom(model);
    }

    /**
     * 创建发展关系
     *
     * @param model
     */
    protected void createDevelopRelationship(MemberFissionModel model, MemberDefinition member) {

        MemberInfo memberInfo = (MemberInfo) member;

        MemberFissionModel req = model;
        //记录推荐关系
        String inviteCode = req.getInviteCode();
        if (!StringUtils.hasText(inviteCode)) {
            return;
        }
        //找到推荐人用户信息
        MemberInfo inviteMember = memberService.findByInviteCode(inviteCode);
        if (inviteMember == null) {
            log.warn("推荐人未找到,inviteCode： {}", inviteCode);
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("推荐人：{}", inviteMember.getId());
        }
        boolean isActive = inviteMember.getEnabled() && MemberVerifyStatus.APPROVED.equals(inviteMember.getVerify());
        if (!isActive) {
            log.warn("推荐人已被禁用：{}", inviteCode);
            return;
        }
        // 创建推荐关系
        CreateMemberPromotionReq createMemberPromotionReq = new CreateMemberPromotionReq();
        createMemberPromotionReq.setFromMemberId(inviteMember.getId());
        createMemberPromotionReq.setType(PromotionType.REGISTER);
        createMemberPromotionReq.setToMemberId(member.getId());
        createMemberPromotionReq.setToMemberNo(memberInfo.getNo());
        memberPromotionService.create(createMemberPromotionReq);
    }


    /**
     * 创建代理账号
     *
     * @param member
     * @param model
     */
    protected void createMemberAgent(MemberDefinition member, MemberRegisterModel model) {

        //找到推荐人用户信息
        MemberFissionModel memberFissionModel = (MemberFissionModel) model;
        String inviteCode = memberFissionModel.getInviteCode();
        CreateMemberAgentReq memberAgentReq = new CreateMemberAgentReq();
        memberAgentReq.setClosed(false)
                .setEnabled(false)
                .setId(member.getId())
                .setName(member.getName()!= null? member.getName() : member.getMobilePhone());
        if (!StringUtils.hasText(inviteCode)) {
            //没有推荐人
            memberAgentReq.setLevel(1)
                    .setLevelPath("#" + member.getId().toString() + "#");
        } else {
            //找到推荐人用户信息
            MemberInfo inviteMember = memberService.findByInviteCode(inviteCode);
            //查找推荐人对应的代理信息
            MemberAgentInfo memberAgentInfo = memberAgentService.findById(inviteMember.getId());
            memberAgentReq.setLevel(memberAgentInfo.getLevel() + 1)
                    .setLevelPath(memberAgentInfo.getLevelPath() + member.getId().toString() + "#");
        }
        memberAgentService.create(memberAgentReq);

    }

    /**
     * 更新推荐人信息
     *
     * @param member
     * @param model
     */
    protected void updateReferrerInfo(MemberDefinition member, MemberRegisterModel model) {
        //找到推荐人用户信息
        MemberFissionModel memberFissionModel = (MemberFissionModel) model;
        String inviteCode = memberFissionModel.getInviteCode();
        if (!StringUtils.hasText(inviteCode)) {
            return;
        }
        //找到推荐人用户信息
        MemberInfo inviteMember = memberService.findByInviteCode(inviteCode);
        //更新推荐人信息
        memberAgentService.editMemberAgent(inviteMember.getId());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
