package com.oak.member.services.depositcashmethod.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



/**
 *  创建DepositCashMethod
 *  2020-6-8 16:51:49
 */
@Schema(description = "创建CreateDepositCashMethodReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateDepositCashMethodReq extends ApiBaseReq {

    @Schema(description = "提现方式代码")
    @NotNull
    @Size(max = 32)
    private String code;

    @Schema(description = "图标")
    private String logo;

    @Schema(description = "名称")
    @NotNull
    @Size(max = 64)
    private String name;

    @Schema(description = "显示名称")
    @Size(max = 32)
    private String showName;

    @Schema(description = "所有的配置的信息，使用json格式保存")
    @NotNull
    @Size(max = 4000)
    private String config;

    @Schema(description = "自动提现的一个限额，单位分，默认：0")
    @NotNull
    private Integer autoAmount;


}
