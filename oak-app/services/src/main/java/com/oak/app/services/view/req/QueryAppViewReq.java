package com.oak.app.services.view.req;

import com.oak.api.model.ApiBaseQueryReq;
import com.oak.app.enums.ViewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 查询客户端视图
 * 2018-6-9 15:03:49
 */
@Schema(description = "查询客户端视图")
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryAppViewReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "视图名称")
    private String name;

    @Schema(description = "视图编码")
    private String code;

    @Schema(description = "视图类型")
    private ViewType type;

    @Schema(description = "访问路径")
    private String path;


}
