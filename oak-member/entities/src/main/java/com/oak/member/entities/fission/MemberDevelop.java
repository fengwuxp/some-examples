package com.oak.member.entities.fission;

import com.oak.member.entities.Member;
import com.oak.member.enums.fission.DevelopMode;
import com.oak.member.enums.fission.DevelopType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wxup
 */
@Schema(description = "用户发展关系")
@Entity
@Table(name = "t_member_develop")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"member", "agent", "parent"})
public class MemberDevelop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @Id
    private Long id;

    @Schema(description = "用户信息")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "id"))
    private Member member;

    @Schema(description = "发展类型")
    @Column(name = "`type`", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private DevelopType type;

    @Schema(description = "传播方式")
    @Column(name = "`mode`", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private DevelopMode mode;

    @Schema(description = "发展人")
    @Column(name = "develop_id")
    private Long developId;

    @Schema(description = "发展人名称")
    @Column(name = "develop_name")
    private String developName;

    @Schema(description = "归属代理商ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "develop_id", insertable = false, updatable = false)
    private MemberAgent agent;

    @Schema(description = "上级发展人")
    @Column(name = "parent_id")
    private Long parentId;

    @Schema(description = "上级发展人用户名称")
    @Column(name = "parent_name")
    private String parentName;

    @Schema(description = "上级发展人用户信息")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Member parent;

    @Schema(description = "推荐人关系路径")
    @Column(name = "parent_path", nullable = false, length = 128)
    private String parentPath;

    @Schema(description ="创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdateTime;


    @Transient
    public List<Long> getParentIds() {
        List<Long> idList = new ArrayList<>();
        if (parentPath != null) {
            String parentPathStr = parentPath.substring(1, parentPath.length() - 1);
            String[] ids = parentPathStr.split("#");
            for (String id : ids) {
                idList.add(Long.valueOf(id));
            }
            java.util.Collections.reverse(idList);
        }
        return idList;
    }

}
