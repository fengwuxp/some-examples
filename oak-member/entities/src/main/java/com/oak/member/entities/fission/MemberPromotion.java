package com.oak.member.entities.fission;

import com.oak.member.entities.Member;
import com.oak.member.logic.enums.PromotionType;
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
@Entity
@Schema(description = "用户推广信息")
@Table(name = "t_member_promotion")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"member"})
public class MemberPromotion implements Serializable {

    @Id
    @Schema(description = "ID")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "推广用户的Id")
    @Column(name = "from_member_id", nullable = false)
    private Long fromMemberId;

    @Schema(description = "推广用户")
    @JoinColumn(name = "from_member_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Schema(description = "推广类型")
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @Schema(description = "被推广用户的no（来源注册时）")
    @Column(name = "to_member_no", nullable = false)
    private String toMemberNo;

    @Schema(description = "被推广用户ID（来源注册时）")
    @Column(name = "to_member_id")
    private Long toMemberId;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;


}
