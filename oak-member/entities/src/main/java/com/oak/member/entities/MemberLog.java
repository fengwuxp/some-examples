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
 * @author wxup
 */
@Entity
@Schema(description = "会员日志")
@Table(name = "t_member_log", indexes = {
        @Index(name = "index_member_log_member_id", columnList = "member_id")
})
@Data
@Accessors(chain = true)
@ToString()
@EqualsAndHashCode(of = "id")
public class MemberLog implements java.io.Serializable {

    private static final long serialVersionUID = -1916404656887028631L;

    @Schema(description = "id")
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "会员ID")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "会员显示名称")
    @Column(name = "show_name", nullable = false, length = 32)
    private String showName;

    @Schema(description = "操作类型")
    @Column(name = "type", nullable = false, length = 16)
    private String type;

    @Schema(description = "IP")
    @Column(name = "ip", length = 32, nullable = false)
    private String ip;

    @Schema(description = "操作者")
    @Column(name = "operator", nullable = false, length = 32)
    private String operator;

    @Schema(description = "操作描述")
    @Column(name = "description", nullable = false, length = 256)
    private String description;

    @Schema(description = "操作日期")
    @Column(name = "operating_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date operatingTime;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;


}
