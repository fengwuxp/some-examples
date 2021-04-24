package com.oak.member.services.memberagent.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ChenXiaoBin
 * on 2020-06-06
 */

@Schema(description = "代理等级信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class MemberAgentInfo {
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "代理名字")
    private String name;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "归属路径")
    private String path;

    @Schema(description = "层级排序")
    private String levelPath;

    @Schema(description = "上级代理ID")
    private Long parentId;

    @Schema(description = "下级代理数量")
    private Integer agentCount = 0;

    @Schema(description = "用户数量")
    private Integer userCount = 0;


    @Schema(description = "是否可用")
    private Boolean enabled;

    @Schema(description = "已关闭")
    private Boolean closed;
}
