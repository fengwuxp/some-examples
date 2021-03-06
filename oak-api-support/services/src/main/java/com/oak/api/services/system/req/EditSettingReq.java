package com.oak.api.services.system.req;

import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema
public class EditSettingReq extends ApiBaseReq {

    @Schema(description ="名称")
    @NotNull
    private String name;

    @Schema(description ="配置值")
    @NotNull
    @Update
    private String value;

    @Schema(description ="配置描述")
    @Update
    private String description;
}
