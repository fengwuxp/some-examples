package com.oak.member.services.memberagent.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author ChenXiaoBin
 * on 2020-06-06
 */
@Schema(description = "创建CreateMemberAgentReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberAgentReq extends ApiBaseReq {

    @Schema(description = "用户id")
    private Long id;


    @Schema(description = "代理名字")
    @NotNull
    private String name;

    @Schema(description = "层级")
    @NotNull
    private Integer level;

    @Schema(description = "归属路径")
    private String path;

    @Schema(description = "层级排序")
    private String levelPath;

    @Schema(description = "上级代理ID")
    private Long parentId;

    @Schema(description = "下级代理数量")
    private Integer agentCount = 0;


    @Schema(description = "是否可用")
    @NotNull
    private Boolean enabled;

    @Schema(description = "已关闭")
    @NotNull
    private Boolean closed;
}
