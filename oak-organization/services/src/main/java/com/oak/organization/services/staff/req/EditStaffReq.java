package com.oak.organization.services.staff.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.organization.enums.StaffAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑员工
 *  2020-1-19 14:23:00
 */
@Schema(description = "编辑员工")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditStaffReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "用户名称")
    @Update
    private String userName;

    @Schema(description = "部门ID")
    @Update
    private Long departmentId;

    @Schema(description = "组织id")
    @Update
    private Long organizationId;

    @Size(max = 20)
    @Schema(description = "组织编码")
    @Update
    private String organizationCode;

    @Size(max = 128)
    @Schema(description = "员工头像")
    @Update
    private String avatarUrl;

    @Size(max = 12)
    @Schema(description = "员工手机号")
    @Update
    private String mobilePhone;

    @Schema(description = "账号类型")
    @Update
    private StaffAccountType accountType;

    @Schema(description = "创建者")
    @Update
    private Long creatorId;

    @Schema(description = "关联的账号")
    @Update
    private Long adminId;

    @Schema(description = "名称")
    @Update
    private String name;

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

    @Schema(description = "关联的性别（true - 女 false - 男  ）")
    @Update
    private Boolean gender;

    @Schema(description = "邮箱")
    @Update
    private String email;

    public EditStaffReq() {
    }

    public EditStaffReq(Long id) {
        this.id = id;
    }
}
