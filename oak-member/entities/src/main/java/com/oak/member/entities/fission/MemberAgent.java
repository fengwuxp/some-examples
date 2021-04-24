package com.oak.member.entities.fission;

import com.oak.member.entities.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxup
 */
@Schema(description = "代理")
@Entity
@Table(name = "t_member_agent")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"member", "parent"})
public class MemberAgent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    @Id
    private Long id;

    @Schema(description = "用户信息")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Member member;

    @Schema(description = "代理名字")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "层级")
    @Column(name = "`level`", nullable = false)
    private Integer level;

    @Schema(description = "归属路径")
    @Column(name = "path")
    private String path;

    @Schema(description = "层级排序")
    @Column(name = "`level_path`")
    private String levelPath;

    @Schema(description = "上级代理ID")
    @Column(name = "parent_id")
    private Long parentId;

    @Schema(description = "下级代理数量")
    @Column(name = "agent_count")
    private Integer agentCount = 0;

    @Schema(description = "用户数量")
    @Column(name = "user_count")
    private Integer userCount = 0;

    @Schema(description = "上级代理信息")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private MemberAgent parent;

    @Schema(description = "已关闭")
    @Column(name = "is_closed", nullable = false)
    private Boolean closed;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;

}
