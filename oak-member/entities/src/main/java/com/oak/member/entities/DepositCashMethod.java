package com.oak.member.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author wxup
 */
@EqualsAndHashCode(of = "id")
@Data
@Schema(description = "提现转账方式")
@Entity
@Table(name = "t_deposit_cash_method", indexes = {
        @Index(name = "index_deposit_cash_method_code", columnList = "code")
})
@ToString(callSuper = true)
@Accessors(chain = true)
public class DepositCashMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Schema(description = "ID")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "提现方式代码")
    @Column(name = "`code`", unique = true, nullable = false, length = 32)
    private String code;

    @Schema(description = "图标")
    @Column(name = "logo")
    private String logo;

    @Schema(description = "显示名称")
    @Column(name = "show_name", length = 32)
    private String showName;

    @Schema(description = "支付手续费（千分比）")
    @Column(name = "fee", nullable = false)
    private Integer fee = 0;

    @Schema(description = "所有的配置的信息，使用json格式保存")
    @Column(name = "withdraw_config", nullable = false)
    @Lob
    private String config;

    @Schema(description = "是否线上转账")
    @Column(name = "is_online", nullable = false)
    private Boolean online = true;

    @Schema(description = "自动提现的一个限额，单位分，默认：0")
    @Column(name = "auto_amount", nullable = false)
    private Integer autoAmount;

    @Schema(description = "是否启用")
    @Column(name = "is_enable", nullable = false)
    protected Boolean enabled = true;

    @Schema(description ="创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;
}
