package com.oak.api.services.log.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 编辑操作日志
 * 2019-6-24 10:17:24
 */
@Schema(description = "编辑操作日志")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOperationalLogReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "操作类型")
    @Size(max = 100)
    @Update
    private String type;

    @Schema(description = "操作名称")
    @Update
    private String action;

    @Schema(description = "操作内容")
    @Size(max = 4000)
    @Update
    private String content;

    @Schema(description = "请求参数")
    @Size(max = 4000)
    @Update
    private String req;

    @Schema(description = "响应")
    @Size(max = 4000)
    @Update
    private String resp;

    @Schema(description = "IP")
    @Size(max = 15)
    @Update
    private String ip;

    @Schema(description = "Url")
    @Update
    private String url;

    @Schema(description = "来源编码")
    @Update
    private String sourceCode;

    public EditOperationalLogReq() {
    }

    public EditOperationalLogReq(Long id) {
        this.id = id;
    }
}
