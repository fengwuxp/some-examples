package com.oak.member.services.depositcashmethod.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 提现方式
* 2020-6-8 16:51:49
*/
@Schema(description ="提现方式")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class DepositCashMethodInfo implements Serializable {

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "提现方式代码")
        private String code;

        @Schema(description = "图标")
        private String logo;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "显示名称")
        private String showName;

        @Schema(description = "支付手续费（千分比）")
        private Integer fee;

        @Schema(description = "所有的配置的信息，使用json格式保存")
        private String config;

        @Schema(description = "是否线上转账")
        private Boolean online;

        @Schema(description = "自动提现的一个限额，单位分，默认：0")
        private Integer autoAmount;

        @Schema(description = "排序代码")
        private Integer orderCode;

        @Schema(description = "是否启用")
        private Boolean enabled;

        @Schema(description = "是否可编辑")
        private Boolean editable;

        @Schema(description = "创建时间")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;

        @Schema(description = "备注")
        private String remark;


}
