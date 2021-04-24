package com.oak.member.services.depositcashmethod.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  编辑提现方式
 *  2020-6-8 16:51:49
 */
@Schema(description = "编辑提现方式")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditDepositCashMethodReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "提现方式代码")
    @Update
    private String code;

    @Schema(description = "图标")
    @Update
    private String logo;

    @Size(max = 64)
    @Schema(description = "名称")
    @Update
    private String name;

    @Size(max = 32)
    @Schema(description = "显示名称")
    @Update
    private String showName;

    @Schema(description = "支付手续费（千分比）")
    @Update
    private Integer fee;

    @Size(max = 4000)
    @Schema(description = "所有的配置的信息，使用json格式保存")
    @Update
    private String config;

    @Schema(description = "是否线上转账")
    @Update
    private Boolean online;

    @Schema(description = "自动提现的一个限额，单位分，默认：0")
    @Update
    private Integer autoAmount;


    public EditDepositCashMethodReq() {
    }

    public EditDepositCashMethodReq(Long id) {
        this.id = id;
    }
}
