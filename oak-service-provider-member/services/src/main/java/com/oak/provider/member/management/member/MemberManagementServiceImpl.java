package com.oak.provider.member.management.member;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.oak.api.helper.SettingValueHelper;
import com.oak.organization.entities.E_Organization;
import com.oak.organization.entities.Organization;
import com.oak.provider.member.cache.MemberSessionCacheHelper;
import com.oak.provider.member.constant.MemberCacheKeyConstant;
import com.oak.provider.member.entities.*;
import com.oak.provider.member.enums.*;
import com.oak.provider.member.management.member.info.AccountInfo;
import com.oak.provider.member.management.member.info.CheckMobilePhoneAndOpenIdInfo;
import com.oak.provider.member.management.member.info.MemberLoginInfo;
import com.oak.provider.member.management.member.req.*;
import com.oak.provider.member.management.third.ThirdService;
import com.oak.provider.member.management.third.info.WxSessionInfo;
import com.oak.provider.member.management.third.info.WxUserInfo;
import com.oak.provider.member.management.third.req.GetWxMaPhoneNumberReq;
import com.oak.provider.member.management.third.req.GetWxMaSessionReq;
import com.oak.provider.member.management.third.req.GetWxMaUserReq;
import com.oak.provider.member.services.account.MemberAccountService;
import com.oak.provider.member.services.account.info.MemberAccountInfo;
import com.oak.provider.member.services.account.req.EditMemberAccountReq;
import com.oak.provider.member.services.accountlog.MemberAccountLogService;
import com.oak.provider.member.services.accountlog.req.CreateMemberAccountLogReq;
import com.oak.provider.member.services.member.MemberService;
import com.oak.provider.member.services.member.info.MemberInfo;
import com.oak.provider.member.services.member.req.CheckMemberReq;
import com.oak.provider.member.services.member.req.CreateMemberReq;
import com.oak.provider.member.services.member.req.EditMemberReq;
import com.oak.provider.member.services.open.MemberOpenService;
import com.oak.provider.member.services.open.req.ChangePasswordReq;
import com.oak.provider.member.services.open.req.CheckBindOpenReq;
import com.oak.provider.member.services.personal.info.MemberPersonalInfo;
import com.oak.provider.member.services.secure.MemberSecureService;
import com.oak.provider.member.services.secure.info.LoginFail;
import com.oak.provider.member.services.secure.info.MemberSecureInfo;
import com.oak.provider.member.services.secure.req.EditMemberSecureReq;
import com.oak.wechat.publish.entities.E_WxOpenConfig;
import com.oak.wechat.publish.entities.WxOpenConfig;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.oak.provider.member.constant.MemberCacheKeyConstant.LOGIN_REFRESH_TOKEN;
import static com.oak.provider.member.constant.MemberCacheKeyConstant.LOGIN_TOKEN_VALID_HOUR;

/**
 * @author laiy
 * create at 2020-02-06 16:11
 * @Description
 */
@Slf4j
@Service
public class MemberManagementServiceImpl implements MemberManagementService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAccountService accountService;

    @Autowired
    private MemberSecureService secureService;

    @Autowired
    private MemberOpenService openService;

    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private MemberAccountLogService memberAccountLogService;

    @Autowired
    private SettingValueHelper settingValueHelper;

    @Autowired(required = true)
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberSessionCacheHelper memberSessionCacheHelper;

    @Autowired
    private ThirdService thirdService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<Long> register(RegisterMemberReq req) {
        if (StringUtils.hasText(req.getMobilePhone())) {
            Long mobileCount = jpaDao.selectFrom(Member.class)
                    .eq(E_Member.mobilePhone, req.getMobilePhone())
                    .count();
            if (mobileCount > 0) {
                return RestfulApiRespFactory.error("该手机号已注册！");
            }
        }
        if (req.getOpenType() != null) {

            CheckBindOpenReq checkBindOpenReq = new CheckBindOpenReq();
            BeanUtils.copyProperties(req, checkBindOpenReq);
            ApiResp<Boolean> checkBindResp = openService.checkBindOpen(checkBindOpenReq);
            if (checkBindResp.isSuccess() && checkBindResp.getData()) {
                return RestfulApiRespFactory.error("此" + req.getOpenType().getDesc() + "已被绑定，请使用其他账号注册");
            }

        }
        if (!req.getNotPassword() && !StringUtils.hasText(req.getLoginPassword())) {
            return RestfulApiRespFactory.error("登陆密码不能为空");
        }

        //注册会员
        CreateMemberReq createMemberReq = new CreateMemberReq();
        BeanUtils.copyProperties(req, createMemberReq);
        createMemberReq.setIdAuth(false)
                .setRegDateTime(new Date())
                .setRegSource(req.getChannelCode())
                .setName(req.getNickName())
                .setMobileAuth(req.getMobileAuth() == null ? Boolean.FALSE : req.getMobileAuth());
        if (req.getVerify() == null) {
            createMemberReq.setVerify(MemberVerifyStatus.PENDING);
        }

        if (req.getOpenType() != null && (req.getOpenType().equals(OpenType.WEIXIN) || req.getOpenType().equals(OpenType.WEIXIN_MA))) {
            FindMemberServiceProviderReq findServiceProviderReq = new FindMemberServiceProviderReq();
            BeanUtils.copyProperties(req, findServiceProviderReq);
            ApiResp<Long> serviceProviderResp = findMemberServiceProviderByAppId(findServiceProviderReq);
            AssertThrow.assertResp(serviceProviderResp);
            createMemberReq.setServiceProviderId(serviceProviderResp.getData());
        } else {
            createMemberReq.setServiceProviderId(0L);
        }

        ApiResp<Long> rsp = memberService.create(createMemberReq);

        //Long memberId = rsp.getData();
        ////会员账号信息
        //CreateMemberAccountReq accountReq = new CreateMemberAccountReq();
        //accountReq.setId(memberId)
        //        .setMoney(0)
        //        .setFrozenMoney(0)
        //        .setFrozenCoupon(0)
        //        .setCoupon(0)
        //        .setPoints(0)
        //        .setFrozenPoints(0)
        //        .setStatus(AccountStatus.AVAILABLE)
        //        .setVipGrade(VipGrade.M_COMMON);
        //ApiResp<Long> accountRsp = accountService.create(accountReq);
        //AssertThrow.assertResp(accountRsp);
        //
        ////会员安全信息
        //CreateMemberSecureReq secureReq = new CreateMemberSecureReq();
        //if (StringUtils.isEmpty(req.getLoginPassword())) {
        //    req.setLoginPassword(SnHelper.makeRandom(6));
        //}
        //secureReq.setId(memberId)
        //        .setLoginPassword(req.getLoginPassword())
        //        .setLoginPwdUpdateTime(new Date());
        //ApiResp<Long> secureRsp = secureService.create(secureReq);
        //AssertThrow.assertResp(secureRsp);
        //
        //if (req.getOpenType() != null) {
        //    //会员绑定信息
        //    CreateMemberOpenReq openReq = new CreateMemberOpenReq();
        //    openReq.setMemberId(memberId)
        //            .setOpenId(req.getOpenId())
        //            .setOpenType(req.getOpenType())
        //            .setUnionId(req.getUnionId());
        //    ApiResp<Long> openRsp = openService.create(openReq);
        //    AssertThrow.assertResp(openRsp);
        //}

        return rsp;
    }

    @Override
    public ApiResp<MemberInfo> registerFromWx(RegisterMemberFromWxReq req) {

        RegisterMemberReq registerMemberReq = new RegisterMemberReq();
        BeanUtils.copyProperties(req, registerMemberReq);
        registerMemberReq.setOpenType(OpenType.WEIXIN)
                .setOpenId(req.getOpenId())
                .setUserName(req.getNickname())
                .setAvatarUrl(req.getHeadImgUrl())
                .setNickName(req.getNickname())
                .setUnionId(req.getUnionId())
                .setGender(formatUserGender(req.getSex()))
                .setNotPassword(Boolean.TRUE)
                .setMobileAuth(Boolean.FALSE)
                .setVerify(MemberVerifyStatus.APPROVED);
        ApiResp<Long> registerResp = register(registerMemberReq);
        AssertThrow.assertResp(registerResp);
        MemberInfo memberInfo = memberService.findById(registerResp.getData());

        return RestfulApiRespFactory.ok(memberInfo);
    }

    @Override
    public ApiResp<Long> registerFromWxMa(RegisterMemberFromWxMaReq req) {
        //获取用户SessionKey和openId
        GetWxMaSessionReq getWxMaSessionReq = new GetWxMaSessionReq();
        getWxMaSessionReq.setCode(req.getCode());
        ApiResp<WxSessionInfo> sessionResult = thirdService.getWxMaSessionInfo(getWxMaSessionReq);
        if (!sessionResult.isSuccess()) {
            return RestfulApiRespFactory.error(sessionResult.getErrorMessage());
        }
        //解密用户信息
        GetWxMaUserReq getWxMaUserReq = new GetWxMaUserReq();
        getWxMaUserReq.setSessionKey(sessionResult.getData().getSessionKey());
        getWxMaUserReq.setEncryptedData(req.getUserEncryptedData());
        getWxMaUserReq.setIvStr(req.getUserIvStr());
        ApiResp<WxUserInfo> userInfo = thirdService.getWxMaUserInfo(getWxMaUserReq);
        //解密用户手机号
        GetWxMaPhoneNumberReq getWxMaPhoneNumberReq = new GetWxMaPhoneNumberReq();
        getWxMaPhoneNumberReq.setSessionKey(sessionResult.getData().getSessionKey());
        getWxMaPhoneNumberReq.setEncryptedData(req.getEncryptedData());
        getWxMaPhoneNumberReq.setIvStr(req.getIvStr());
        ApiResp<String> phoneNumberInfo = thirdService.getWxMaPhoneNumber(getWxMaPhoneNumberReq);

        RegisterMemberReq registerReq = new RegisterMemberReq();
        BeanUtils.copyProperties(req, registerReq);
        registerReq.setOpenType(OpenType.WEIXIN_MA)
                .setUserName(userInfo.getData().getNickname())
                .setOpenId(sessionResult.getData().getOpenid())
                .setUnionId(userInfo.getData().getUnionId())
                .setMobilePhone(phoneNumberInfo.getData())
                .setNotPassword(Boolean.TRUE)
                .setMobileAuth(Boolean.FALSE)
                .setVerify(MemberVerifyStatus.APPROVED);
        ApiResp<Long> rsp = register(registerReq);
        return rsp;
    }

    @Override
    public ApiResp<AccountInfo> getMemberInfo(MemberAccountInfoReq req) {
        //ApiResp<MemberAccountInfo> resp = RestfulApiRespFactory.created()
        AccountInfo accountInfo = new AccountInfo();
        MemberInfo member = memberService.findById(req.getId());
        if (member == null) {
            return RestfulApiRespFactory.error("账号不存在");
        }
        BeanUtils.copyProperties(member, accountInfo);
        MemberAccountInfo memberAccount = accountService.findById(member.getId());
        accountInfo.setMoney(memberAccount.getMoney())
                .setFrozenMoney(memberAccount.getFrozenMoney())
                .setCoupon(memberAccount.getFrozenCoupon())
                .setPoints(memberAccount.getPoints())
                .setFrozenPoints(memberAccount.getFrozenPoints())
                .setStatus(memberAccount.getStatus())
                .setVipGrade(memberAccount.getVipGrade());
        return RestfulApiRespFactory.created(accountInfo);
    }

    @Override
    public ApiResp<MemberPersonalInfo> getMemberPersonalInfo(MemberAccountInfoReq req) {
        Member member = jpaDao.selectFrom(Member.class)
                .eq(E_Member.id, req.getId())
                .findOne();
        if (member == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }

        MemberPersonal personal = jpaDao.selectFrom(MemberPersonal.class)
                .eq(E_MemberPersonal.id, req.getId())
                .findOne();
        if (personal == null) {
            return RestfulApiRespFactory.error("实名信息不存在，请上传实名信息");
        }

        MemberPersonalInfo personalInfo = new MemberPersonalInfo();
        BeanUtils.copyProperties(personal, personalInfo);

        return RestfulApiRespFactory.ok(personalInfo);
    }

    @Override
    public ApiResp<MemberLoginInfo> login(MemberLoginReq req) {
        MemberLoginInfo loginInfo = new MemberLoginInfo();
        //检查参数
        if (LoginModel.PASSWORD.equals(req.getLoginModel())) {
            if (StringUtils.isEmpty(req.getUserName()) && StringUtils.isEmpty(req.getMobilePhone())) {
                return RestfulApiRespFactory.error("登录账号或手机号没有获取到");
            }
        } else if (LoginModel.OPEN_ID.equals(req.getLoginModel())) {
            if (req.getOpenType() == null || StringUtils.isEmpty(req.getOpenId())) {
                return RestfulApiRespFactory.error("OPENID为空");
            }
        } else if (LoginModel.REFRESH_TOKEN.equals(req.getLoginModel())) {
            if (StringUtils.isEmpty(req.getToken())) {
                return RestfulApiRespFactory.error("TOKEN为空");
            }
        }

        //查找用户
        ApiResp<MemberInfo> checkResp = checkLoginMember(req);
        AssertThrow.assertResp(checkResp);

        //检查用户状态
        MemberInfo memberInfo = checkResp.getData();
        if (!memberInfo.getEnable()) {
            return RestfulApiRespFactory.error("用户已被禁用");
        } else if (!MemberVerifyStatus.APPROVED.equals(memberInfo.getVerify())) {
            return RestfulApiRespFactory.error("用户未审核");
        }

        BeanUtils.copyProperties(memberInfo, loginInfo);

        //获取TOKEN
        MemberToken memberToken = saveLogin(req.getLoginModel(), memberInfo, req.getRemoteIp());
        loginInfo.setToken(memberToken.getToken());
        //TOKEN有效秒数
        loginInfo.setTokenExpireDate(memberToken.getExpirationDate());

        memberSessionCacheHelper.join(memberToken.getToken(), loginInfo);
        return RestfulApiRespFactory.ok(loginInfo);
    }

    @Override
    public ApiResp<MemberLoginInfo> unilogin(UniloginReq req) {
        if (LoginModel.PASSWORD.equals(req.getLoginModel())
                && (StringUtils.isEmpty(req.getPassword())
                || (StringUtils.isEmpty(req.getUserName())
                && StringUtils.isEmpty(req.getMobilePhone())))) {
            return RestfulApiRespFactory.error("用户名/手机号或密码为空");
        }

        if (LoginModel.OPEN_ID.equals(req.getLoginModel())
                && (req.getOpenType() == null
                || (StringUtils.isEmpty(req.getOpenId()))
                && StringUtils.isEmpty(req.getUnionId()))) {
            return RestfulApiRespFactory.error("第三方开放平台，必填参数为空");
        }

        // 是否已注册
        boolean registered = exixtsMember(req);
        MemberLoginInfo memberInfo = null;

        if (registered) {
            // 去登录
            MemberLoginReq loginReq = new MemberLoginReq();
            BeanUtils.copyProperties(req, loginReq);
            ApiResp<MemberLoginInfo> loginResp = login(loginReq);
            if (!loginResp.isSuccess()) {
                return loginResp;
            }
            memberInfo = loginResp.getData();
        } else if (req.getLoginModel().equals(LoginModel.OPEN_ID) || StringUtils.hasText(req.getMobilePhone())) {
            //未注册
            RegisterMemberReq registerReq = new RegisterMemberReq();
            BeanUtils.copyProperties(req, registerReq);
            registerReq.setLoginPassword(req.getPassword());
            if (req.getLoginModel().equals(LoginModel.OPEN_ID)) {
                registerReq.setNotPassword(true);
                registerReq.setVerify(MemberVerifyStatus.APPROVED);
            }
            registerReq.setUserName(StringUtils.hasText(req.getUserName()) ? req.getUserName() : req.getNickName());
            ApiResp<Long> registerResp = register(registerReq);
            if (!registerResp.isSuccess()) {
                return RestfulApiRespFactory.error(registerResp.getErrorMessage());
            }

            //内部登录
            MemberInfo member = memberService.findById(registerResp.getData());
            memberInfo = new MemberLoginInfo();
            BeanUtils.copyProperties(member, memberInfo);
            MemberToken memberToken = saveLogin(LoginModel.INTERNAL, member, req.getRemoteIp());

            memberInfo.setToken(memberToken.getToken());
            memberInfo.setTokenExpireDate(memberToken.getExpirationDate());


        } else {
            return RestfulApiRespFactory.error("登录失败");
        }

        return RestfulApiRespFactory.ok(memberInfo);
    }

    private boolean exixtsMember(UniloginReq evt) {
        long count = 0;
        List<OpenType> wxOpenTypes = new ArrayList<>();
        wxOpenTypes.add(OpenType.WEIXIN);
        wxOpenTypes.add(OpenType.WEIXIN_OPEN);
        wxOpenTypes.add(OpenType.WEIXIN_MA);

        if (LoginModel.PASSWORD.equals(evt.getLoginModel())) {
            SelectDao<Member> memberDao = jpaDao.selectFrom(Member.class);
            if (!StringUtils.isEmpty(evt.getMobilePhone())) {
                memberDao.appendWhere(!StringUtils.isEmpty(evt.getMobilePhone()), "mobilePhone= :?", evt.getMobilePhone());
            } else {
                memberDao.appendWhere("(userName= :? or mobilePhone= :? or email= :? or no= :?)",
                        evt.getUserName(), evt.getUserName(), evt.getUserName(), evt.getUserName());
            }
            count = memberDao.count();
        } else if (LoginModel.OPEN_ID.equals(evt.getLoginModel())) {

            SelectDao<MemberOpen> selectDao = jpaDao.selectFrom(MemberOpen.class)
                    .appendWhere(!StringUtils.isEmpty(evt.getOpenId()) && !StringUtils.isEmpty(evt.getUnionId()),
                            "( openId = :? or unionId = :?)", evt.getOpenId(), evt.getUnionId())
                    .eq("openId", evt.getOpenId())
                    .eq("unionId", evt.getUnionId());

            if (wxOpenTypes.contains(evt.getOpenType())) {
                selectDao.in("openType", wxOpenTypes.toArray());
            } else {
                selectDao.eq("openType", evt.getOpenType());
            }

            count = selectDao.count();
        }
        return count > 0;
    }

    @Override
    public ApiResp checkPassword(CheckPasswordReq req) {
        MemberSecure memberSecure = jpaDao.find(MemberSecure.class, req.getUid());

        if (memberSecure == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }

        boolean isCheck = settingValueHelper.getSettingValue(MemberCacheKeyConstant.MEMBER_LOGIN_FAIL_CHECK, false);
        if (isCheck) {
            //失败阀值控制
            String checkLoginFail = checkLoginFail(memberSecure.getId());
            if (checkLoginFail != null && !checkLoginFail.isEmpty()) {
                return RestfulApiRespFactory.error(checkLoginFail);
            }
        }

        try {

            if (!req.getPassword().equals(memberSecure.getLoginPassword())) {

                String msg = "登录密码错误！";
                if (isCheck) {
                    Integer count = secureService.addLoginFail(memberSecure.getId());
                    if (count <= 0) {
                        msg = "抱歉，您登录失败已达到5次，账号将被锁定1小时！";
                    } else if (count < 4) {
                        msg = "登录密码错误，今天还可尝试" + count + "次将被锁定！";
                    }
                }
                return RestfulApiRespFactory.error(msg);
            }
            secureService.resetLoginFail(req.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RestfulApiRespFactory.ok();
    }

    private ApiResp<MemberInfo> checkLoginMember(MemberLoginReq evt) {
        MemberInfo memberInfo = null;
        if (LoginModel.PASSWORD.equals(evt.getLoginModel())) {
            SelectDao<Member> memberSelectDao = jpaDao.selectFrom(Member.class);
            if (!StringUtils.isEmpty(evt.getMobilePhone())) {
                memberSelectDao.appendWhere(!StringUtils.isEmpty(evt.getMobilePhone()), "mobilePhone= :?", evt.getMobilePhone());
            } else {
                memberSelectDao.appendWhere("(userName= :? or mobilePhone= :? or email= :? or no= :?)",
                        evt.getUserName(), evt.getUserName(), evt.getUserName(), evt.getUserName());
            }
            memberInfo = memberSelectDao
                    .findOne(MemberInfo.class);

            if (memberInfo == null) {
                return RestfulApiRespFactory.error("用户不存在");
            }

            //密码登陆
            ApiResp<String> checkResp = checkPassword(
                    new CheckPasswordReq(memberInfo.getId(), evt.getPassword()));
            AssertThrow.assertResp(checkResp);
        } else if (LoginModel.OPEN_ID.equals(evt.getLoginModel())) {
            List<OpenType> wxOpenTypes = new ArrayList<>();
            wxOpenTypes.add(OpenType.WEIXIN);
            wxOpenTypes.add(OpenType.WEIXIN_MA);
            wxOpenTypes.add(OpenType.WEIXIN_OPEN);
            //第三方快捷登录
            SelectDao<MemberOpen> selectDao = jpaDao.selectFrom(MemberOpen.class)
                    .appendWhere(!StringUtils.isEmpty(evt.getOpenId()) && !StringUtils.isEmpty(evt.getUnionId()),
                            "( openId = :? or unionId = :?)", evt.getOpenId(), evt.getUnionId())
                    .eq("openId", evt.getOpenId())
                    .eq("unionId", evt.getUnionId());

            if (wxOpenTypes.contains(evt.getOpenType())) {
                selectDao.in("openType", wxOpenTypes.toArray());
            } else {
                selectDao.eq("openType", evt.getOpenType());
            }

            MemberOpen memberOpen = selectDao.findOne();

            if (memberOpen == null) {
                return RestfulApiRespFactory.error("未绑定第三方平台");
            }

            if (memberOpen.getExpirationDate() != null
                    && memberOpen.getExpirationDate().getTime() < System.currentTimeMillis()) {
                return RestfulApiRespFactory.error("第三方平台登录凭证已失效");
            }

            // 同步openId和unionId
            if (!StringUtils.isEmpty(evt.getOpenId())) {
                int update = jpaDao.updateTo(MemberOpen.class)
                        .eq("memberId", memberOpen.getMemberId())
                        .isNull("openId")
                        .eq("openType", evt.getOpenType())
                        .appendColumn("openId", evt.getOpenId())
                        .appendColumn("updateTime", new Date())
                        .update();
                log.debug("【" + memberOpen.getOpenType().getDesc() + "】" +
                        "[" + update + "]更新openId=" + evt.getOpenId());
            }

            if (!StringUtils.isEmpty(evt.getUnionId())) {
                int update = jpaDao.updateTo(MemberOpen.class)
                        .eq("memberId", memberOpen.getMemberId())
                        .isNull("unionId")
                        .eq("openType", evt.getOpenType())
                        .appendColumn("unionId", evt.getUnionId())
                        .appendColumn("updateTime", new Date())
                        .update();
                log.debug("【" + memberOpen.getOpenType().getDesc() + "】" +
                        "[" + update + "]更新openId=" + evt.getOpenId());
            }

            memberInfo = memberService.findById(memberOpen.getMemberId());
        } else if (LoginModel.REFRESH_TOKEN.equals(evt.getLoginModel())) {
            MemberToken memberToken = jpaDao.selectFrom(MemberToken.class)
                    .eq("token", evt.getToken()).findOne();

            if (memberToken == null
                    || (memberToken.getExpirationDate() != null
                    && memberToken.getExpirationDate().getTime() < System.currentTimeMillis())) {

                return RestfulApiRespFactory.error("登录凭证不存在或已过期");
            }

            memberInfo = memberService.findById(memberToken.getId());
        }

        if (memberInfo == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }

        memberInfo.setMemberSecureInfo(null);
        return RestfulApiRespFactory.ok(memberInfo);
    }

    private String checkLoginFail(Long uid) {
        LoginFail loginFail = secureService.findLoginFail(uid);
        long currentMillis = System.currentTimeMillis();
        if (loginFail != null) {

            if (loginFail.getFailTime() != null && loginFail.getFailTime() < currentMillis - (24 * 3600 * 1000L)) {
                //密码输错时间超过一小时
                loginFail.setFailTime(null);
                loginFail.setCount(0);

                secureService.saveLoginFail(uid, loginFail);
            } else {

                if (loginFail.getLockTime() != null && loginFail.getCount() >= 5) {
                    //在一小时内
                    if (loginFail.getLockTime() > currentMillis - (3600 * 1000L)) {
                        return "抱歉，您登录失败已达到5次，账号将被锁定1小时！请稍候再试";
                    } else {
                        loginFail.setLockTime(null);
                        loginFail.setCount(0);
                        secureService.saveLoginFail(uid, loginFail);
                    }
                }
            }
        }
        return null;
    }

    public Gender formatUserGender(Integer wxSex) {
        switch (wxSex) {
            case 1:
                return Gender.MAN;
            case 2:
                return Gender.WOMEN;
            default:
                return Gender.SECRET;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<Void> recharge(RechargeReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() + req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertTrue(editResp.getErrorMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(0)
                .setCurrFrozenMoney(accountInfo.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription("充值余额")
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.RECHARGE.name())
                .setCoupon(0)
                .setPoints(0)
                .setFrozenPoints(0)
                .setCurrFrozenPoints(0)
                .setCurrCoupon(0)
                .setFrozenCoupon(0)
                .setOperator(req.getOperator())
                .setCurrFrozenCoupon(0);
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<Void> deductMoney(DeductMoneyReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        AssertThrow.assertTrue("余额不足", accountInfo.getMoney() > req.getAmount());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() - req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertTrue(editResp.getErrorMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(-req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(0)
                .setCurrFrozenMoney(accountInfo.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription(req.getReason())
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.DEDUCT.name())
                .setCoupon(0)
                .setPoints(0)
                .setFrozenPoints(0)
                .setCurrFrozenPoints(0)
                .setCurrCoupon(0)
                .setFrozenCoupon(0)
                .setOperator(req.getOperator())
                .setCurrFrozenCoupon(0);
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> freezeMoney(FreezeMoneyReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        AssertThrow.assertTrue("余额不足", accountInfo.getMoney() > req.getAmount());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() - req.getAmount())
                .setFrozenMoney(accountInfo.getFrozenMoney() + req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertTrue(editResp.getErrorMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(-req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(req.getAmount())
                .setCurrFrozenMoney(editMemberAccountReq.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription(req.getReason())
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.FREEZE.name())
                .setCoupon(0)
                .setPoints(0)
                .setFrozenPoints(0)
                .setCurrFrozenPoints(0)
                .setCurrCoupon(0)
                .setFrozenCoupon(0)
                .setOperator(req.getOperator())
                .setCurrFrozenCoupon(0);
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> unfreezeMoney(UnfreezeMoneyReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        AssertThrow.assertTrue("冻结余额不足", accountInfo.getFrozenMoney() > req.getAmount());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() + req.getAmount())
                .setFrozenMoney(accountInfo.getFrozenMoney() - req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertTrue(editResp.getErrorMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(-req.getAmount())
                .setCurrFrozenMoney(editMemberAccountReq.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription(req.getReason())
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.UNFREEZE.name())
                .setCoupon(0)
                .setPoints(0)
                .setFrozenPoints(0)
                .setCurrFrozenPoints(0)
                .setCurrCoupon(0)
                .setFrozenCoupon(0)
                .setOperator(req.getOperator())
                .setCurrFrozenCoupon(0);
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }


    private MemberToken saveLogin(LoginModel loginModel, MemberInfo memberInfo, String ip) {
        //记录用户日志
        MemberLog log = new MemberLog();
        log.setMemberId(memberInfo.getId());
        log.setType(loginModel.name() + " 登录");
        log.setOperator(memberInfo.getUserName() == null ? memberInfo.getMobilePhone() : memberInfo.getUserName());
        log.setOperatingTime(new Date());
        log.setShowName(!StringUtils.isEmpty(memberInfo.getNickName()) ? memberInfo.getNickName() : memberInfo.getUserName());
        log.setDescription("登录成功");
        log.setIp(ip);
        jpaDao.create(log);

        //更新最近一次上线时间
        jpaDao.updateTo(MemberSecure.class)
                .appendColumn(E_MemberSecure.lastLoginFailLockTime, new Date())
                .eq(E_MemberSecure.id, memberInfo.getId())
                .update();

        //生成token
        String token = jwtTokenProvider.generateAccessToken(memberInfo.getId() + memberInfo.getNo() + memberInfo.getRegDateTime().getTime() + System.currentTimeMillis())
                .getToken();

        MemberToken memberToken = jpaDao.find(MemberToken.class, memberInfo.getId());
        boolean isNew = false;
        String oldToken = null;
        if (memberToken == null) {
            memberToken = new MemberToken();
            memberToken.setId(memberInfo.getId()).setToken(token);
            isNew = true;
        } else {
            oldToken = memberToken.getToken();
        }
        if (settingValueHelper.getSettingValue(LOGIN_REFRESH_TOKEN, false)) {
            memberToken.setToken(token);
        }
        memberToken.setLoginTime(new Date());

        //有效小时数
        Integer tokenHour = Integer.valueOf(settingValueHelper.getSettingValue(LOGIN_TOKEN_VALID_HOUR, 24));
        memberToken.setExpirationDate(new Date(memberToken.getLoginTime().getTime() + tokenHour * 3600 * 1000L));

        if (isNew) {
            jpaDao.create(memberToken);
        } else {
            jpaDao.save(memberToken);
        }
        return memberToken;
    }

    @Override
    public ApiResp<CheckMobilePhoneAndOpenIdInfo> checkMobilePhoneAndOpenIdWxMa(CheckMobilePhoneAndOpenIdWxMaReq req) {
        CheckMobilePhoneAndOpenIdInfo result = new CheckMobilePhoneAndOpenIdInfo();

        //解密数据得到手机号
        GetWxMaPhoneNumberReq getWxMaPhoneNumberReq = new GetWxMaPhoneNumberReq();
        getWxMaPhoneNumberReq.setSessionKey(req.getSessionKey());
        getWxMaPhoneNumberReq.setEncryptedData(req.getEncryptedData());
        getWxMaPhoneNumberReq.setIvStr(req.getIvStr());
        ApiResp<String> getMobilePhoneResp = thirdService.getWxMaPhoneNumber(getWxMaPhoneNumberReq);
        AssertThrow.assertResp(getMobilePhoneResp);

        String mobilePhone = getMobilePhoneResp.getData();
        result.setMobilePhone(mobilePhone);

        //手机号是否已注册
        CheckMemberReq checkMemberEvt = new CheckMemberReq();
        checkMemberEvt.setMobilePhone(mobilePhone);
        ApiResp<Long> checkMember = memberService.checkMember(checkMemberEvt);
        result.setRegisted(checkMember.getData() != null);

        if (Boolean.TRUE.equals(result.getRegisted())) {
            Long memberId = checkMember.getData();
            CheckBindOpenReq checkBindOpenEvt = new CheckBindOpenReq();
            checkBindOpenEvt.setOpenType(OpenType.WEIXIN_MA);
            checkBindOpenEvt.setMemberId(memberId);
            ApiResp<Boolean> checkBindOpenResp = openService.checkBindOpen(checkBindOpenEvt);
            result.setMobBindOpen(checkBindOpenResp.getData());
        }

        //是否已绑定OPEIN
        CheckBindOpenReq checkBindOpenEvt = new CheckBindOpenReq();
        BeanUtils.copyProperties(req, checkBindOpenEvt);
        checkBindOpenEvt.setOpenType(OpenType.WEIXIN_MA);
        ApiResp<Boolean> checkBindOpenResp = openService.checkBindOpen(checkBindOpenEvt);
        result.setBindOpen(checkBindOpenResp.getData());

        return RestfulApiRespFactory.ok(result);
    }

    @Override
    @Transactional
    public ApiResp<MemberLoginInfo> refreshMemberToken(RefreshMemberTokenReq req) {
        MemberLoginInfo memberLoginInfo = memberSessionCacheHelper.get(req.getToken());
        MemberToken memberToken = new MemberToken();

        if (memberLoginInfo == null) {
            return RestfulApiRespFactory.error("登录凭证不存在或已过期");
        }
        memberToken.setRefreshToken(memberLoginInfo.getToken());
        memberToken.setRefreshExpirationDate(new Date());
        //生成token
        JwtTokenPair.JwtTokenPayLoad token = jwtTokenProvider.generateAccessToken(memberLoginInfo.getId() + memberLoginInfo.getUserName()
                + System.currentTimeMillis());
        if (settingValueHelper.getSettingValue(LOGIN_REFRESH_TOKEN, false).equals(true)) {
            memberLoginInfo.setToken(token.getToken());
        }
        //有效小时数
        Integer tokenHour = Integer.valueOf(settingValueHelper.getSettingValue(LOGIN_TOKEN_VALID_HOUR, 24));
        memberLoginInfo.setTokenExpireDate(new Date(System.currentTimeMillis() + tokenHour * 3600 * 1000L));
        memberToken.setToken(token.getToken());
        memberToken.setExpirationDate(memberLoginInfo.getTokenExpireDate());
        jpaDao.save(memberToken);
        memberSessionCacheHelper.join(token.getToken(), memberLoginInfo);
        return RestfulApiRespFactory.ok(memberLoginInfo);
    }

    @Override
    public ApiResp<MemberInfo> queryMemberByOpenId(QueryMemberByOpenIdReq req) {
        List<OpenType> wxOpenTypes = new ArrayList<>();
        wxOpenTypes.add(OpenType.WEIXIN);
        wxOpenTypes.add(OpenType.WEIXIN_OPEN);
        wxOpenTypes.add(OpenType.WEIXIN_MA);
        SelectDao<MemberOpen> selectDao = jpaDao.selectFrom(MemberOpen.class)
                .eq(E_MemberOpen.openId, req.getOpenId());

        if (wxOpenTypes.contains(req.getOpenType())) {
            selectDao.in(E_MemberOpen.openType, wxOpenTypes.toArray());
        } else {
            selectDao.eq(E_MemberOpen.openType, req.getOpenType());
        }
        MemberOpen open = selectDao.findOne();
        if (open != null) {
            MemberInfo memberInfo = memberService.findById(open.getMemberId());
            return RestfulApiRespFactory.ok(memberInfo);
        }
        return RestfulApiRespFactory.error("不存在绑定此OpenId的用户");
    }

    @Override
    public ApiResp<Void> modifyAvatar(ModifyAvatarReq req) {
        EditMemberReq editReq = new EditMemberReq();
        editReq.setAvatarUrl(req.getUrl()).setId(req.getUid()).setLastUpdateTime(new Date());
        return memberService.edit(editReq);
    }

    @Override
    public ApiResp changePassword(ChangePasswordReq req) {
        MemberInfo memberInfo = memberService.findById(req.getUid());
        if (memberInfo == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }
        MemberSecureInfo secureInfo = secureService.findById(req.getUid());
        if (secureInfo == null) {
            return RestfulApiRespFactory.error("用户安全信息不存在");
        }
        if (req.getOldPassword().equals(secureInfo.getPayPassword())) {
            return RestfulApiRespFactory.error("密码错误");
        }
        EditMemberSecureReq eSecureReq = new EditMemberSecureReq();
        eSecureReq.setId(req.getUid())
                .setLoginPassword(req.getNewPassword())
                .setLoginPwdUpdateTime(new Date());
        ApiResp resp = secureService.edit(eSecureReq);
        AssertThrow.assertResp(resp);
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp frozen(FrozenReq req) {
        MemberInfo member = memberService.findById(req.getUid());
        if (member == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }

        Calendar ca = new GregorianCalendar();
        if (req.getDays() > -1) {
            ca.add(Calendar.DAY_OF_YEAR, req.getDays());
        }
        String atTime = DateFormatUtils.format(ca, "yyyy-MM-dd HH:mm:ss");

        boolean success = jpaDao.updateTo(Member.class)
                .appendColumn(E_Member.frozenDate, (req.getDays() > -1 ? atTime : null))
                .eq(E_Member.id, req.getUid())
                .update() > 0;
        if (!success) {
            return RestfulApiRespFactory.error("操作失败");
        }

        return RestfulApiRespFactory.ok();
    }


    @Override
    public ApiResp<Long> findMemberServiceProviderByAppId(FindMemberServiceProviderReq req) {
        if (!req.getOpenType().equals(OpenType.WEIXIN) && !req.getOpenType().equals(OpenType.WEIXIN_MA)) {
            return RestfulApiRespFactory.error("错误的APPID");
        }

        WxOpenConfig config = jpaDao.selectFrom(WxOpenConfig.class, "e")
                .appendWhere(req.getOpenType().equals(OpenType.WEIXIN), E_WxOpenConfig.officialAccountAppId + "= :?", req.getWxAppId())
                .appendWhere(req.getOpenType().equals(OpenType.WEIXIN_MA), E_WxOpenConfig.miniProgramAppId + "= :?", req.getWxAppId())
                .findOne();
        if (config == null) {
            return RestfulApiRespFactory.error("未绑定服务商");
        }
        Organization organization = jpaDao.selectFrom(Organization.class)
                .eq(E_Organization.id, config.getOrganizationId())
                .findOne();
        if (config == null) {
            return RestfulApiRespFactory.error("服务商不存在");
        }
        return RestfulApiRespFactory.ok(organization.getId());
    }
}
