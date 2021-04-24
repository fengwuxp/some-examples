package test.com.oak.member.business.deposit;

import com.oak.member.business.deposit.DefaultTransferManager;
import com.oak.member.business.deposit.req.ManualTransferReq;
import com.oak.member.constant.DepositCashMethodInitialSettingConstant;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import test.com.oak.member.business.OakMemberApplicationTest;

/**
 * @Classname DefaultTransferManagerTest
 * @Description 提现测试
 * @Date 2020/6/10 13:12
 * @Created by 44487
 */

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakMemberApplicationTest.class})
@Slf4j
public class DefaultTransferManagerTest {

    @Autowired
    private DefaultTransferManager defaultTransferManager;

    @Test
    public void withdraw(){
        ManualTransferReq req = new ManualTransferReq();
        req.setId(30003L)
                .setOperator("李四")
                .setDepositCashCode(DepositCashMethodInitialSettingConstant.OFFLINE_BANK_CARD_WITHDRAWAL_CODE)
                .setSuccess( true )
                .setPaymentResult( "转账成功" )
                .setPaymentSn(RandomStringUtils.randomAlphabetic( 12 ))
                .setPayCertificate( "这是支付凭证A" );

        ApiResp<Void> apiResp = defaultTransferManager.transfer(req);

        Assert.isTrue( apiResp.isSuccess(),apiResp.getErrorMessage() );
    }

}
