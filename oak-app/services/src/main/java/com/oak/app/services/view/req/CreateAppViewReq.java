package com.oak.app.services.view.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.app.enums.ViewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * @author ChenXiaoBin
 * on 2020-05-21
 */
@Schema(description = "创建客户端视图定义")
@Data
public class CreateAppViewReq extends ApiBaseReq {

    @Schema(description = "视图名称")
    private String name;

    @Schema(description = "视图编码")
    private String code;

    @Schema(description = "视图类型")
    private ViewType type;

    @Schema(description = "访问路径")
    private String path;

    @Schema(description = "参数模板（{key:value}）")
    private String params;
}
