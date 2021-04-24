package com.wuxp.security.example.services.simple.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 编辑example例子
 * 2020-2-16 10:20:18
 */
@Schema(description = "编辑example例子")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditExampleEntityReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(min = 0, max = 16)
    @Schema(description = "name")
    @Update
    private String name;

    @Max(value = 200)
    @Schema(description = "年龄")
    @Update
    private Integer age;

    @Schema(description = "头像")
    @Update
    private String avatarUrl;

    @Schema(description = "账户余额")
    @Update
    private Integer money;

    @Schema(description = "生日")
    @Update
    private Date birthday;

    @Schema(description = "星期")
    @Update
    private Week week;

    @Schema(description = "例子id")
    @Update
    private Long exampleId;

    @Schema(description = "是否删除")
    @Update
    private Boolean deleted;

    @Schema(description = "排序代码")
    @Update
    private Integer orderCode;

    @Schema(description = "是否允许")
    @Update
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @Update
    private Boolean editable;

    @Schema(description = "更新时间")
    @Update
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @Update
    private String remark;

    public EditExampleEntityReq() {
    }

    public EditExampleEntityReq(Long id) {
        this.id = id;
    }
}
