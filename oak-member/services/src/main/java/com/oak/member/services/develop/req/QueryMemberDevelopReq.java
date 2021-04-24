package com.oak.member.services.develop.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.fission.DevelopMode;
import com.oak.member.enums.fission.DevelopType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询用户发展关系
 *  2020-6-3 21:55:15
 */
@Schema(description = "查询用户发展关系")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberDevelopReq extends ApiBaseQueryReq {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "加载用户信息")
    @Fetch(value = "member", condition = "#_val==true")
    private Boolean loadMember;

    @Schema(description = "发展类型")
    private DevelopType type;

    @Schema(description = "传播方式")
    private DevelopMode mode;

    @Schema(description = "发展人")
    private Long developId;

    @Schema(description = "发展人名称")
    private String developName;

    @Schema(description = "加载归属代理商ID")
    @Fetch(value = "agent", condition = "#_val==true")
    private Boolean loadAgent;

    @Schema(description = "推荐人用户ID")
    private Long parentId;

    @Schema(description = "推荐人用户名称")
    private String parentName;

    @Schema(description = "加载推荐用户信息")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadParent;

    @Schema(description = "推荐人关系路径")
    private String parentPath;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryMemberDevelopReq() {
    }

    public QueryMemberDevelopReq(Long id) {
        this.id = id;
    }
}
