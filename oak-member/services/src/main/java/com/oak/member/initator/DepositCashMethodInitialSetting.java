package com.oak.member.initator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname DepositCashMethodInitialSetting
 * @Description 提现方式初始化对象
 * @Date 2020/6/9 15:13
 * @Created by 44487
 */

@Schema(description = "提现方式初始化对象")
@Data
public class DepositCashMethodInitialSetting implements Serializable {

    @Schema(description = "提现方式代码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "显示名称")
    private String showName;

    @Schema(description = "所有的配置的信息，使用json格式保存")
    private String config;

    @Schema(description = "自动提现的一个限额，单位分，默认：0")
    private Integer autoAmount;

    @Schema(description = "排序代码")
    private Integer orderCode;

}
