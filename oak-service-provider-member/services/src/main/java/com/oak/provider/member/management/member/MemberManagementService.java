package com.oak.provider.member.management.member;

import com.oak.provider.member.management.member.info.AccountInfo;
import com.oak.provider.member.management.member.info.CheckMobilePhoneAndOpenIdInfo;
import com.oak.provider.member.management.member.info.MemberLoginInfo;
import com.oak.provider.member.management.member.req.*;
import com.oak.provider.member.services.member.info.MemberInfo;
import com.oak.provider.member.services.open.req.ChangePasswordReq;
import com.oak.provider.member.services.personal.info.MemberPersonalInfo;
import com.wuxp.api.ApiResp;

/**
 * 用户管理
 *
 * @author laiy
 * create at 2020-02-06 16:11
 * @Description
 */
public interface MemberManagementService {


    /**
     * 注册账号
     *
     * @param req
     * @return
     */
    ApiResp<Long> register(RegisterMemberReq req);

    /**
     * 从微信公众号注册帐号
     *
     * @param req
     * @return
     */
    ApiResp<MemberInfo> registerFromWx(RegisterMemberFromWxReq req);

    /**
     * 从微信小程序注册帐号
     *
     * @param req
     * @return
     */
    ApiResp<Long> registerFromWxMa(RegisterMemberFromWxMaReq req);

    /**
     * 获取账户信息
     *
     * @return
     */
    ApiResp<AccountInfo> getMemberInfo(MemberAccountInfoReq req);

    /**
     * 获取用户实名信息
     *
     * @return
     */
    ApiResp<MemberPersonalInfo> getMemberPersonalInfo(MemberAccountInfoReq req);

    /**
     * 用户登录
     *
     * @param req
     * @return
     */
    ApiResp<MemberLoginInfo> login(MemberLoginReq req);

    /**
     * 统一登录注册
     */
    ApiResp<MemberLoginInfo> unilogin(UniloginReq req);


    /**
     * 充值余额
     *
     * @param req
     * @return
     */
    ApiResp<Void> recharge(RechargeReq req);

    /**
     * 密码验证
     */
    ApiResp checkPassword(CheckPasswordReq req);


    /**
     * 扣除余额
     *
     * @param req
     * @return
     */
    ApiResp<Void> deductMoney(DeductMoneyReq req);

    /**
     * 冻结余额
     *
     * @param req
     * @return
     */
    ApiResp<Void> freezeMoney(FreezeMoneyReq req);

    /**
     * 解冻余额
     *
     * @param req
     * @return
     */
    ApiResp<Void> unfreezeMoney(UnfreezeMoneyReq req);

    /**
     * 检查手机号和微信OPENID
     */
    ApiResp<CheckMobilePhoneAndOpenIdInfo> checkMobilePhoneAndOpenIdWxMa(CheckMobilePhoneAndOpenIdWxMaReq req);

    /**
     * 刷新用户Token
     */
    ApiResp<MemberLoginInfo> refreshMemberToken(RefreshMemberTokenReq req);

    /**
     * 根据openId查找用户
     */
    ApiResp<MemberInfo> queryMemberByOpenId(QueryMemberByOpenIdReq req);

    /**
     * 修改用户头像信息
     */
    ApiResp<Void> modifyAvatar(ModifyAvatarReq req);

    /**
     * 修改密码
     */
    ApiResp changePassword(ChangePasswordReq req);

    /**
     * 冻结用户
     */
    ApiResp frozen(FrozenReq req);

    /**
     * 根据AppId查找用户服务商
     * @param req
     * @return
     */
    ApiResp<Long> findMemberServiceProviderByAppId(FindMemberServiceProviderReq req);

}
