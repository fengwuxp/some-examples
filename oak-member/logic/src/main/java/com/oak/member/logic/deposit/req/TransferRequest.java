package com.oak.member.logic.deposit.req;

/**
 * 提现转账请求
 */
public interface TransferRequest {


    /**
     * 提现记录ID编号
     * @return
     */
    Long getId();

    /**
     * 支付操作员
     */
    String getOperator();

    /**
     * 提现的方式
     *
     * @return
     */
    String getDepositCashCode();

    /**
     * 转帐成功
      */
    Boolean isSuccess();

    /**
     * 转帐返回结果
     */
     String getPaymentResult();

//    /**
//     * 转帐返回信息
//     */
//     String getPaymentInfo();

    /**
     * 转帐返回流水号
     */
     String getPaymentSn();

    /**
     * 支付凭证
     */
    String getPayCertificate();

    /**
     * 实际的手续费
     * @return
     */
    Integer getActualFee();

}
