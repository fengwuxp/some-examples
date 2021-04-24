package com.oak.member.business.deposit;

import com.oak.member.constant.WithdrawBusinessConstant;
import com.oak.member.enums.WithdrawStatus;
import com.oak.member.logic.deposit.TransferProvider;
import com.oak.member.logic.deposit.req.TransferRequest;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.req.ChangeAccountReq;
import com.oak.member.services.memberdepositcash.MemberDepositCashService;
import com.oak.member.services.memberdepositcash.info.MemberDepositCashInfo;
import com.oak.member.services.memberdepositcash.req.EditMemberDepositCashReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @Classname BankCardWithdrawProvider
 * @Description 银行卡提现方式
 * @Date 2020/6/9 1:57
 * @Created by 44487
 */
@Service("bankcard_transfer_provider")
@Slf4j
public class BankCardTransferProvider implements TransferProvider {
    /**
     * 银行卡线下转账
     */
    public static final String BLANK_OFFLINE_TRANSFER_CODE = "OFFLINE_BANK_CARD";

    @Autowired
    private MemberDepositCashService memberDepositCashService;

    @Autowired
    private MemberAccountService memberAccountService;

    /**
     * 此为手动提现方式，即通过线下转账之后，手动输入转账信息（支付凭证，支付流水号等信息）
     *
     * @param request
     * @return
     */
    @Override
    public ApiResp<Void> transfer(TransferRequest request) {

        MemberDepositCashInfo memberDepositCashInfo = memberDepositCashService.findById(request.getId());

        EditMemberDepositCashReq editMemberDepositCashReq = new EditMemberDepositCashReq();
        editMemberDepositCashReq.setId(request.getId())
                .setAuditDate(new Date());

        if (request.isSuccess()) {

            Integer fee = request.getActualFee() != null ? request.getActualFee() : memberDepositCashInfo.getHandlingFee();

            //成功
            editMemberDepositCashReq.setWithdrawStatus(WithdrawStatus.SUCCESS)
                    .setPayCertificate(request.getPayCertificate())
                    .setPaySn(request.getPaymentSn())
                    .setAuditContent(request.getPaymentResult())
                    .setPayDate(new Date())
                    .setHandlingFee(fee)
                    .setActualAmount(memberDepositCashInfo.getAmount() - fee);
        } else {
            //失败
            editMemberDepositCashReq.setWithdrawStatus(WithdrawStatus.FAIL)
                    .setAuditContent(request.getPaymentResult());
        }

        //修改
        ApiResp<Void> memberWithdrawApiresp = memberDepositCashService.edit(editMemberDepositCashReq);
        if (!memberWithdrawApiresp.isSuccess()) {
            return memberWithdrawApiresp;
        }

        //变更金额
        ChangeAccountReq changeAccountReq = new ChangeAccountReq();
        changeAccountReq.setId(memberDepositCashInfo.getMemberId())
                .setOperator(request.getOperator())
                .setTargetId(memberDepositCashInfo.getSn());

        if (request.isSuccess()) {
            //成功
            changeAccountReq.setMoney(-memberDepositCashInfo.getAmount())
                    .setFrozenMoney(-memberDepositCashInfo.getAmount())
                    .setBusinessType(WithdrawBusinessConstant.WITHDRAW_OPERATING_SUCCESS )
                    .setDescription(MessageFormat.format("会员（id:{0}提现成功，余额减少：{1}，冻结金额减少{2}）"
                            , memberDepositCashInfo.getMemberId(), memberDepositCashInfo.getAmount(), memberDepositCashInfo.getAmount()));

        } else {
            //失败
            changeAccountReq.setMoney(0)
                    .setFrozenMoney(-memberDepositCashInfo.getAmount())
                    .setBusinessType( WithdrawBusinessConstant.WITHDRAW_OPERATING_FAIL )
                    .setDescription(MessageFormat.format("会员（id:{0}）提现失败，冻结金额减少：{1}",
                            memberDepositCashInfo.getMemberId(), memberDepositCashInfo.getAmount()));

        }

        ApiResp<Void> memberAccountApiresp = memberAccountService.changeAccount(changeAccountReq);
        if (!memberAccountApiresp.isSuccess()) {
            return memberAccountApiresp;
        }
        return RestfulApiRespFactory.ok();

    }

    @Override
    public boolean supports(String code) {
        return BLANK_OFFLINE_TRANSFER_CODE.equals(code);
    }
}
