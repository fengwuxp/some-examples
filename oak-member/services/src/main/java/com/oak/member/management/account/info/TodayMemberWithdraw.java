package com.oak.member.management.account.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Date： 2020/9/4 10:05
 *
 * 今日会员提现概览数据信息
 *
 * @author ZHM
 */
@Data
@Accessors(chain = true)
public class TodayMemberWithdraw implements Serializable {

    @Schema(description = "提现金额")
    private Integer amount;

    @Schema(description = "提现次数")
    private Integer number;

}
