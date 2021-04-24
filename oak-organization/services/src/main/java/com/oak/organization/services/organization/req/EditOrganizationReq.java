package com.oak.organization.services.organization.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.organization.enums.ApprovalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑组织
 *  2020-1-19 13:18:21
 */
@Schema(description = "编辑组织")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOrganizationReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "审核状态")
    @Update
    private ApprovalStatus status;

    @Size(max = 50)
    @Schema(description = "联系人")
    @Update
    private String contacts;

    @Size(max = 50)
    @Schema(description = "联系电话")
    @Update
    private String contactMobilePhone;

    @Schema(description = "LOGO")
    @Update
    private String logo;

    @Size(max = 20)
    @Schema(description = "区域ID")
    @Update
    private String areaId;

    @Schema(description = "区域名称")
    @Update
    private String areaName;

    @Schema(description = "详细地址")
    @Update
    private String address;

    @Schema(description = "最后到期日期")
    @Update
    private Date lastAuthEndDate;

    @Size(max = 64)
    @Schema(description = "机构拼音首字母")
    @Update
    private String pinyinInitials;

    @Schema(description = "父ID")
    @Update
    private Long parentId;

    @Size(max = 16)
    @Schema(description = "类型")
    @Update
    private String type;

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

    @Schema(description = "创建者")
    @Update
    private Long creatorId;

    @Size(max = 1000)
    @Schema(description = "备注")
    @Update
    private String remark;

    public EditOrganizationReq() {
    }

    public EditOrganizationReq(Long id) {
        this.id = id;
    }
}
