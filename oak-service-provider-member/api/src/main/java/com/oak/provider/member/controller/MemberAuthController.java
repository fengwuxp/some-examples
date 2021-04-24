package com.oak.provider.member.controller;

import com.oak.provider.member.services.personal.MemberPersonalService;
import com.oak.provider.member.services.personal.info.MemberPersonalInfo;
import com.oak.provider.member.services.personal.req.CheckMemberPersonalReq;
import com.oak.provider.member.services.personal.req.CreateMemberPersonalReq;
import com.oak.provider.member.services.personal.req.EditMemberPersonalReq;
import com.oak.provider.member.services.personal.req.QueryMemberPersonalReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author laiy
 * create at 2020-03-05 15:34
 * @Description
 */
@RestController
@RequestMapping("/member_auth")
@Slf4j
@Tag(name = "认证服务", description = "认证服务")
public class MemberAuthController {


    @Autowired
    private MemberPersonalService memberPersonalService;


    @PostMapping("/save_member_personal")
    @Operation(summary = "个人实名认证申请", description = "个人实名认证申请")
    ApiResp<Long> saveMemberPersonal(@RequestBody @Valid CreateMemberPersonalReq req) {
        return memberPersonalService.create(req);
    }

    @PutMapping("/check_member_personal")
    @Operation(summary = "个人实名认证核验", description = "个人实名认证核验")
    ApiResp<Integer> checkMemberPersonal(@RequestBody @Valid CheckMemberPersonalReq req) {
        return memberPersonalService.checkMemberPersonal(req);
    }

    @PutMapping("/edit_member_personal")
    @Operation(summary = "个人实名认证编辑", description = "个人实名认证编辑")
    ApiResp<Void> editPersonal(EditMemberPersonalReq req) {
        return memberPersonalService.edit(req);
    }

    @GetMapping("/query_member_personal_list")
    @Operation(summary = "个人实名认证查询", description = "个人实名认证查询")
    ApiResp<Pagination<MemberPersonalInfo>> queryMemberPersonal(QueryMemberPersonalReq req) {
        return RestfulApiRespFactory.queryOk(memberPersonalService.query(req));
    }

}
