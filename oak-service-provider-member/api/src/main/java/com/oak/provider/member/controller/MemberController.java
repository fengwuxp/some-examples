package com.oak.provider.member.controller;

import com.levin.commons.dao.JpaDao;
import com.oak.provider.member.management.member.MemberManagementService;
import com.oak.provider.member.management.member.info.AccountInfo;
import com.oak.provider.member.management.member.info.CheckMobilePhoneAndOpenIdInfo;
import com.oak.provider.member.management.member.info.MemberLoginInfo;
import com.oak.provider.member.management.member.req.*;
import com.oak.provider.member.services.member.MemberService;
import com.oak.provider.member.services.member.info.MemberInfo;
import com.oak.provider.member.services.member.req.EditMemberReq;
import com.oak.provider.member.services.member.req.QueryMemberReq;
import com.oak.provider.member.services.open.req.ChangePasswordReq;
import com.oak.provider.member.services.personal.info.MemberPersonalInfo;
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
 * create at 2020-02-19 14:56
 * @Description
 */

@RestController
@RequestMapping("/member")
@Slf4j
@Tag(name = "用户服务", description = "用户相关服务")
public class MemberController {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JpaDao jpaDao;

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册")
    public ApiResp<Long> register(@RequestBody @Valid RegisterMemberReq dto) {
        return memberManagementService.register(dto);
    }

    /**
     * 用户注册 (从微信小程序注册)
     *
     * @return
     */
    @PostMapping("/register_wx_ma")
    @Operation(summary = "用户注册 (从微信小程序注册)", description = "用户注册 (从微信小程序注册)")
    public ApiResp<Long> registerFromWxMa(@RequestBody RegisterMemberFromWxMaReq dto) {
        return memberManagementService.registerFromWxMa(dto);
    }

    /**
     * 获取账户信息
     *
     * @return
     */
    @GetMapping("/account_info")
    @Operation(summary = "获取账户信息)", description = "获取账户信息")
    public ApiResp<AccountInfo> getMemberAccountInfo(MemberAccountInfoReq dto) {
        return memberManagementService.getMemberInfo(dto);
    }

    /**
     * 获取账户实名信息
     *
     * @return
     */
    @GetMapping("/account_personal_info")
    @Operation(summary = "获取账户实名信息)", description = "获取账户实名信息")
    public ApiResp<MemberPersonalInfo> getMemberPersonalInfo(MemberAccountInfoReq dto) {
        return memberManagementService.getMemberPersonalInfo(dto);
    }

    /**
     * 用户登录
     * @param req
     * @return
     */
    @GetMapping("/login")
    @Operation(summary = "用户登录)", description = "用户登录")
    public ApiResp<MemberLoginInfo> login(MemberLoginReq req) {
        return memberManagementService.login(req);
    }

    /**
     * 统一登录注册
     * @param req
     * @return
     */
    @GetMapping("/unilogin")
    @Operation(summary = "统一登录注册)", description = "统一登录注册")
    public ApiResp<MemberLoginInfo> unilogin(UniloginReq req) {
        return memberManagementService.unilogin(req);
    }

    /**
     * 检查手机号和微信OPENID
     */
    @GetMapping("/check_mobile_openid")
    @Operation(summary = "检查手机号和微信OPENID)", description = "检查手机号和微信OPENID")
    public ApiResp<CheckMobilePhoneAndOpenIdInfo> checkMobilePhoneAndOpenIdWxMa(CheckMobilePhoneAndOpenIdWxMaReq req) {
        return memberManagementService.checkMobilePhoneAndOpenIdWxMa(req);
    }

    /**
     * 刷新用户Token
     */
    @GetMapping("/refresh_token")
    @Operation(summary = "刷新用户Token)", description = "刷新用户Token")
    public ApiResp<MemberLoginInfo> refreshMemberToken(RefreshMemberTokenReq req) {
        return memberManagementService.refreshMemberToken(req);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/edit_member")
    @Operation(summary = "修改用户信息)", description = "修改用户信息")
    public ApiResp<Void> editMember(@RequestBody EditMemberReq req) {
        return memberService.edit(req);
    }

    /**
     * 修改用户头像信息
     */
    @PostMapping("/modify_avatar")
    @Operation(summary = "修改用户头像信息)", description = "修改用户头像信息")
    public ApiResp<Void> modifyAvatar(@RequestBody @Valid ModifyAvatarReq req) {
        return memberManagementService.modifyAvatar(req);
    }

    /**
     * 修改密码
     */
    @GetMapping("/change_password")
    @Operation(summary = "修改密码)", description = "修改密码")
    public ApiResp changePassword(ChangePasswordReq req) {
        return memberManagementService.changePassword(req);
    }

    /**
     * 冻结用户
     */
    @GetMapping("/frozen_member")
    @Operation(summary = "冻结用户)", description = "冻结用户")
    public ApiResp frozen(FrozenReq req) {
        return memberManagementService.frozen(req);
    }

    /**
     * 搜索用户
     */
    @GetMapping("/query_member")
    @Operation(summary = "搜索用户)", description = "搜索用户")
    public ApiResp<Pagination<MemberInfo>> queryMember(QueryMemberReq req) {
        return RestfulApiRespFactory.ok(memberService.query(req));
    }


}
