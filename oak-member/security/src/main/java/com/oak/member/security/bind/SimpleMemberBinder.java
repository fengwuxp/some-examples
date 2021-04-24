package com.oak.member.security.bind;

import com.oak.member.logic.bind.BindOpenModel;
import com.oak.member.logic.bind.MemberBinder;
import com.oak.member.security.bind.req.BindOpenReq;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.EditMemberReq;
import com.oak.member.services.member.req.QueryMemberReq;
import com.oak.member.services.open.MemberOpenService;
import com.oak.member.services.open.info.MemberOpenInfo;
import com.oak.member.services.open.req.CreateMemberOpenReq;
import com.oak.member.services.open.req.DeleteMemberOpenReq;
import com.oak.member.services.open.req.QueryMemberOpenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.QueryType;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

/**
 * @author wxup
 */
@Slf4j
public class SimpleMemberBinder implements MemberBinder {

    @Autowired
    private MemberOpenService memberOpenService;

    @Autowired
    private MemberService memberService;


    @Override
    public ApiResp<Void> bindOpen(BindOpenModel model) {

        QueryMemberOpenReq req = new QueryMemberOpenReq();
        req.setOpenType(model.getOpenType())
                .setMemberId(model.getMemberId())
                .setOpenId(model.getOpenId())
                .setQueryType(QueryType.QUERY_NUM);
        if (memberOpenService.query(req).getTotal() > 0) {
            return RestfulApiRespFactory.error("该用户已经绑定过该平台");
        }

        BindOpenReq bindOpenReq = (BindOpenReq) model;
        CreateMemberOpenReq memberOpenReq = new CreateMemberOpenReq();
        memberOpenReq.setMemberId(bindOpenReq.getMemberId())
                .setOpenType(memberOpenReq.getOpenType())
                .setOpenId(memberOpenReq.getOpenId())
                .setUnionId(memberOpenReq.getUnionId())
                .setBindChannelCode(bindOpenReq.getBindChannelCode())
                .setSubscribe(bindOpenReq.getSubscribe())
                .setBindInfo("绑定" + memberOpenReq.getOpenType().getDesc());
        ApiResp<Long> resp = memberOpenService.create(memberOpenReq);
        if (!resp.isSuccess()) {
            return RestfulApiRespFactory.error("绑定第三方平台失败");
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> unbindOpen(BindOpenModel model) {
        QueryMemberOpenReq req = new QueryMemberOpenReq();
        req.setOpenType(model.getOpenType())
                .setMemberId(model.getMemberId())
                .setOpenId(model.getOpenId())
                .setQuerySize(1);
        MemberOpenInfo openInfo = memberOpenService.query(req).getFirst();
        if (openInfo == null) {
            return RestfulApiRespFactory.error("绑定记录不存在");
        }
        ApiResp<Void> resp = memberOpenService.delete(new DeleteMemberOpenReq(openInfo.getId()));
        if (!resp.isSuccess()) {
            return RestfulApiRespFactory.error("解绑第三方平台失败");
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> bindMobilePhone(@NotNull Long memberId, @NotNull String mobilePhone) {

        QueryMemberReq req = new QueryMemberReq();
        req.setId(memberId)
                .setQuerySize(1);
        MemberInfo memberInfo = memberService.query(req).getFirst();
        if (memberInfo == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }
        if (mobilePhone.equals(memberInfo.getMobilePhone())) {
            return RestfulApiRespFactory.ok();
        }
        EditMemberReq editMemberReq = new EditMemberReq();
        editMemberReq.setId(memberId)
                .setMobilePhone(mobilePhone)
                .setMobileAuth(true);
        ApiResp<Void> edit = memberService.edit(editMemberReq);
        if (!edit.isSuccess()) {
            return RestfulApiRespFactory.error("绑定手机失败");
        }


        return RestfulApiRespFactory.ok();
    }

//    @Override
//    public ApiResp<Void> unBindMobilePhone(@NotNull Long memberId, @NotNull String mobilePhone) {
//        QueryMemberReq req = new QueryMemberReq();
//        req.setId(memberId)
//                .setQuerySize(1);
//        MemberInfo memberInfo = memberService.query(req).getFirst();
//        if (memberInfo == null) {
//            return RestfulApiRespFactory.error("用户不存在");
//        }
//        EditMemberReq editMemberReq = new EditMemberReq();
//        editMemberReq.setId(memberId)
//                .setMobilePhone(mobilePhone)
//                .setMobileAuth(false);
//        ApiResp<Void> edit = memberService.edit(editMemberReq);
//        if (!edit.isSuccess()){
//            return RestfulApiRespFactory.error("绑定手机失败");
//        }
//
//        return RestfulApiRespFactory.ok();
//    }


}
