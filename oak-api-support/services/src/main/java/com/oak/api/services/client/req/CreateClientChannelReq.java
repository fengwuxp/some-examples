package com.oak.api.services.client.req;

import com.oak.api.enums.ClientType;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建ClientChannel
 *  2020-3-11 11:05:46
 */
@Schema(description = "创建CreateClientChannelReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateClientChannelReq extends ApiBaseReq {

    @Schema(description = "编号")
    @NotNull
    @Size(max = 32)
    private String code;

    @Schema(description = "名称")
    @NotNull
    @Size(max = 128)
    private String name;

    @Schema(description = "客户端类型")
    private ClientType clientType;

    @Schema(description = "排序")
    @NotNull
    private Integer orderIndex;

    @Schema(description = "启用")
    @NotNull
    private Boolean enabled;

}
