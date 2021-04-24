package com.oak.member.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * 用户银行账号信息表
 * 可以是，银行卡，支付宝账户，微信账户
 *
 * @author wxup
 */
@Entity
@Schema(description = "用户银行账号信息表")
@Table(name = "t_blank_account", indexes = {
        @Index(name = "index_blank_account_member_id", columnList = "member_id")
})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString()
public class MemberBlankAccount implements java.io.Serializable {

    @Id
    @Schema(description = "id")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "账户类型")
    @Column(name = "blank_type", nullable = false, length = 16)
    private String blankType;

    @Schema(description = "账户关联会员id")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "开户行名称")
    @Column(name = "bank_name")
    private String bankName;

    @Schema(description = "开户姓名")
    @Column(name = "account_name", nullable = false, length = 32)
    private String accountName;

    @Schema(description = "账号")
    @Column(name = "account_number", nullable = false, length = 32)
    private String accountNumber;

    @Schema(description = "账户预留手机号")
    @Column(name = "member_phone", length = 12)
    private String memberPhone;

    @Schema(description = "是否删除")
    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;


    @Schema(description = "日志日期")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;

}
