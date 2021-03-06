package com.oak.member.management.account;


import com.oak.api.helper.SettingValueHelper;
import com.oak.member.constant.DepositCashConstant;
import com.oak.member.constant.MemberAccountSettingsConstant;
import com.oak.member.constant.WithdrawBusinessConstant;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.WithdrawStatus;
import com.oak.member.management.account.info.TodayMemberWithdraw;
import com.oak.member.management.account.req.ApplyWithdrawalReq;
import com.oak.member.management.account.req.CheckWithdrawReq;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.ChangeAccountReq;
import com.oak.member.services.depositcashmethod.DepositCashMethodService;
import com.oak.member.services.depositcashmethod.info.DepositCashMethodInfo;
import com.oak.member.services.memberdepositcash.MemberDepositCashService;
import com.oak.member.services.memberdepositcash.info.MemberDepositCashInfo;
import com.oak.member.services.memberdepositcash.req.CreateMemberDepositCashReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wxup
 */
@Service
@Slf4j
public class MemberAccountManagementServiceImpl implements MemberAccountManagementService {


    @Autowired
    private MemberAccountService memberAccountService;

    @Autowired
    private SettingValueHelper settingValueHelper;

    @Autowired
    private DepositCashMethodService depositCashMethodService;

    @Autowired
    private MemberDepositCashService memberDepositCashService;

    @Override
    public ApiResp<Void> checkWithdrawRequest(CheckWithdrawReq req) {

        //??????????????????????????????
        boolean withdrawEnableValue = settingValueHelper.getSettingValue(DepositCashConstant.SYSTEM_WITHDRAWAL_ENABLE,
                DepositCashConstant.DEFAULT_SYSTEM_WITHDRAWAL_ENABLE);
        if (!withdrawEnableValue) {
            return RestfulApiRespFactory.error("?????????????????????????????????");
        }

        //???????????????????????????????????????
        boolean systemMemberRealNameEnable = settingValueHelper.getSettingValue(MemberAccountSettingsConstant.SYSTEM_MEMBER_REAL_NAME_ENABLE,
                MemberAccountSettingsConstant.DEFAULT_SYSTEM_MEMBER_REAL_NAME_ENABLE);

        if (systemMemberRealNameEnable) {
            //?????????true ???????????????????????????????????????????????????
            //TODO ?????????????????????
        }

        //??????????????????????????????????????????
        MemberAccountInfo accountInfo = memberAccountService.findById(req.getMemberId());

        if (accountInfo == null) {
            return RestfulApiRespFactory.error("??????????????????????????????????????????????????????");
        }
        if (AccountStatus.FREEZE.equals(accountInfo.getStatus())) {
            return RestfulApiRespFactory.error("????????????????????????????????????");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    @Transactional
    public ApiResp<Void> applyWithdrawal(ApplyWithdrawalReq req) {

        //??????????????????????????????
        ApiResp<Void> preconditionResp = checkWithdrawRequest(new CheckWithdrawReq().setMemberId(req.getMemberId()));
        if (!preconditionResp.isSuccess()) {
            return RestfulApiRespFactory.error(preconditionResp.getErrorMessage());
        }

        //??????????????????
        //???1????????????????????? ?????? ????????????????????????
        int minWithdrawValue = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_MIN_AMOUNT,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_MIN_AMOUNT);
        if (req.getAmount() < minWithdrawValue) {
            return RestfulApiRespFactory.error("??????????????????????????????????????????");
        }

        // (2) ?????????????????? ?????? ????????????????????????
        int maxWithdrawValue = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_MAX_AMOUNT,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_MAX_AMOUNT);

        if (req.getAmount() > maxWithdrawValue) {
            return RestfulApiRespFactory.error("????????????????????????????????????????????????");
        }

        // ???3??? ?????????????????????????????????????????????
        MemberAccountInfo accountInfo = memberAccountService.findById(req.getMemberId());
        if (req.getAmount() > accountInfo.getAvailableMoney()) {
            return RestfulApiRespFactory.error("????????????????????????");
        }

        // ???4??? ??????????????????????????????
        DepositCashMethodInfo withdrawMethodInfo = depositCashMethodService.findByCode(req.getDepositCashCode());
        if (withdrawMethodInfo == null || !withdrawMethodInfo.getEnabled()) {
            return RestfulApiRespFactory.error("????????????????????????");
        }

        //?????????????????????????????????
        TodayMemberWithdraw todayMemberWithdraw = getTodayMemberWithdraw(req.getMemberId());
        //????????????????????????????????????
        int dailyAmount = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_DAILY_LIMIT_AMOUNT,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_DAILY_LIMIT_AMOUNT);

        // (5)  ???????????????????????????????????? + ???????????????????????? ??????  ?????? ????????????????????????
        if (todayMemberWithdraw.getAmount() + req.getAmount() > dailyAmount) {
            return RestfulApiRespFactory.error("????????????????????????????????????");
        }

        //????????????????????????????????????
        int dailyLimitNumber = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_DAILY_LIMIT_NUMBER,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_LIMIT_NUMBER);

        //???6??? ?????????????????????????????? + ???????????????????????????1???????????? ?????? ?????????????????????
        if (todayMemberWithdraw.getNumber() + 1 > dailyLimitNumber) {
            return RestfulApiRespFactory.error("????????????????????????????????????");
        }

        //?????? ?????????
        int cashFee = withdrawMethodInfo.getFee();
        int fee = cashFee > 0 ? new BigDecimal(cashFee)
                .divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(req.getAmount()))
                .setScale(0, BigDecimal.ROUND_UP).intValue() : 0;

        //????????????????????????
        CreateMemberDepositCashReq createCashReq = new CreateMemberDepositCashReq();
        createCashReq.setWithdrawStatus(WithdrawStatus.APPLY)
                .setApplyDate(new Date())
                .setMemberId(req.getMemberId())
                .setAccountName(req.getBankUser())
                .setAccountNumber(req.getBankNo())
                .setWithdrawCode(req.getDepositCashCode())
                .setAmount(req.getAmount())
                .setHandlingFee(fee)
                .setActualAmount(req.getAmount() - fee);

        ApiResp<Long> withdrawApiResp = memberDepositCashService.create(createCashReq);
        if (!withdrawApiResp.isSuccess()) {
            return RestfulApiRespFactory.error("????????????????????????");
        }

        MemberDepositCashInfo memberDepositCashInfo = memberDepositCashService.findById(withdrawApiResp.getData());

        ChangeAccountReq changeAccountReq = new ChangeAccountReq();
        changeAccountReq.setId(req.getMemberId())
                .setFrozenMoney(req.getAmount())
                .setBusinessType(StringUtils.isBlank(req.getBusinessType()) ? WithdrawBusinessConstant.WITHDRAW_OPERATING : req.getBusinessType())
                .setTargetId(String.valueOf(memberDepositCashInfo.getId()))
                .setDescription(MessageFormat.format("?????????id???{0}??????????????????????????????????????????{1}", req.getMemberId(), req.getAmount()))
                .setOperator(req.getOperator());
        //????????????????????????
        ApiResp<Void> memberAccountLogApiResp = memberAccountService.changeAccount(changeAccountReq);

        if (!memberAccountLogApiResp.isSuccess()) {
            return memberAccountLogApiResp;
        }

        //??????????????????
        //???????????????????????? ????????????????????????????????????
        boolean autoWithdrawEnable = settingValueHelper.getSettingValue(DepositCashConstant.SYSTEM_AUTO_WITHDRAWAL_ENABLE,
                DepositCashConstant.DEFAULT_SYSTEM_AUTO_WITHDRAWAL_ENABLE);

        if (req.getAmount() > withdrawMethodInfo.getAutoAmount() && autoWithdrawEnable) {
            //??????????????????
            //????????????????????????
            // TODO ??????????????????


        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public TodayMemberWithdraw getTodayMemberWithdraw(Long memberId) {

        TodayMemberWithdraw todayMemberWithdraw = new TodayMemberWithdraw();

        //?????????????????????????????????
        List<MemberDepositCashInfo> cashInfoList = memberDepositCashService.queryByMemberDaily(memberId);

        if (cashInfoList != null && !cashInfoList.isEmpty()) {

            int withdrawTotalAmount = cashInfoList.stream()
                    .map(MemberDepositCashInfo::getAmount)
                    .reduce(0, Integer::sum);

            todayMemberWithdraw.setAmount(withdrawTotalAmount)
                    .setNumber(cashInfoList.size());

        } else {
            todayMemberWithdraw.setAmount(0)
                    .setNumber(0);
        }

        return todayMemberWithdraw;
    }
}
