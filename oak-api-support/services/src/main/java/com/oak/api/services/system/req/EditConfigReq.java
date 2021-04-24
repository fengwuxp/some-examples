package com.oak.api.services.system.req;

import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


@Schema(description ="获取配置项值")
@Data
public class EditConfigReq extends ApiBaseReq {

    @Schema(description ="配置名称")
    private String name;

    @Schema(description ="配置值")
    @Update
    private String value;

    @Schema(description ="更新日期")
    @Update
    private Date updateTime = new Date();

    public EditConfigReq() {
    }

}
