package com.oak.member.services.develop.info;

import com.levin.commons.service.domain.Desc;
import com.oak.member.enums.fission.DevelopMode;
import com.oak.member.enums.fission.DevelopType;
import com.oak.member.services.member.info.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户发展关系
 * 2020-6-3 21:55:15
 */
@Schema(description = "用户发展关系")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"memberInfo", "agentInfo", "parentInfo",})
public class MemberDevelopInfo implements Serializable {

    @Schema(description = "用户ID")
    private Long id;

    @Desc(value = "", code = "member")
    @Schema(description = "用户信息")
    private MemberInfo memberInfo;

    @Schema(description = "发展类型")
    private DevelopType type;

    @Schema(description = "传播方式")
    private DevelopMode mode;

    @Schema(description = "归属代理ID")
    private Long agentId;

    @Schema(description = "归属代理名称")
    private String agentName;


    @Schema(description = "推荐人用户ID")
    private Long parentId;

    @Schema(description = "推荐人用户名称")
    private String parentName;

    @Desc(value = "", code = "parent")
    @Schema(description = "推荐用户信息")
    private MemberInfo parentInfo;

    @Schema(description = "推荐人关系路径")
    private String parentPath;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;



}
