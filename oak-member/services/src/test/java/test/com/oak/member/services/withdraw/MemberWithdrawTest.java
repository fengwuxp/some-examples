package test.com.oak.member.services.withdraw;

import com.oak.member.constant.DepositCashMethodInitialSettingConstant;
import com.oak.member.management.account.MemberAccountManagementService;
import com.oak.member.management.account.req.ApplyWithdrawalReq;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.req.ChangeAccountReq;
import com.oak.member.services.depositcashmethod.DepositCashMethodService;
import com.oak.member.services.depositcashmethod.req.CreateDepositCashMethodReq;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import test.com.oak.member.OakApplicationTest;

import java.text.MessageFormat;

/**
 * @Classname MemberWithdrawTest
 * @Description 提现流程测试
 * @Date 2020/6/9 19:00
 * @Created by 44487
 */

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class MemberWithdrawTest {

    @Autowired
    private DepositCashMethodService depositCashMethodService;

    @Autowired
    private MemberAccountManagementService memberAccountManagementService;

    @Autowired
    private MemberAccountService memberAccountService;

    //初始化一个提现配置方式
    @Test
    public void initWithdrawMethod() {
        CreateDepositCashMethodReq req = new CreateDepositCashMethodReq();
        req.setCode(DepositCashMethodInitialSettingConstant.OFFLINE_BANK_CARD_WITHDRAWAL_CODE)
                .setName("银行卡提现")
                .setShowName("测试银行卡提现方式")
                .setConfig("{}")
                .setAutoAmount(0);

        //初始化一个手动提现方式
        ApiResp<Long> apiResp = depositCashMethodService.create(req);

        Assert.isTrue(apiResp.isSuccess(), "操作失败");
    }


    @Test
    public void withdrawApply() {

        //提现申请
        ApplyWithdrawalReq req = new ApplyWithdrawalReq();
        req.setMemberId(5L)
                .setOperator("李四")
                .setAmount(45)
                .setDepositCashCode(DepositCashMethodInitialSettingConstant.OFFLINE_BANK_CARD_WITHDRAWAL_CODE)
                .setBankName("建设银行")
                .setBankNo("5612 4568 5266 9658")
                .setBankUser("李四A");


        ApiResp<Void> apiResp = memberAccountManagementService.applyWithdrawal(req);

        Assert.isTrue(apiResp.isSuccess(), apiResp.getErrorMessage());

    }

    @Test
    @Rollback( value = false)
    public void memberAccountIncrease() {

        long memberId = 5L;

        Integer amount = -100;

        //账户金额添加操作
        ChangeAccountReq changeAccountReq = new ChangeAccountReq();
        changeAccountReq.setId(5L)
                .setFrozenMoney(amount)
                .setBusinessType("测试金额" + (amount > 0 ? "增加" : "减少"))
                .setTargetId(RandomStringUtils.randomAlphabetic(12))
                .setDescription(MessageFormat.format("会员（id：{0}）发起提现操作，冻结金额增加{1}", memberId, amount))
                .setOperator("李四");

        //变更会员账户金额
        ApiResp<Void> memberAccountLogApiResp = memberAccountService.changeAccount(changeAccountReq);

        Assert.isTrue( memberAccountLogApiResp.isSuccess(),memberAccountLogApiResp.getErrorMessage() );

    }


}
