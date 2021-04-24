package com.oak.organization.management.organization.req;


import com.oak.api.model.ApiBaseReq;
import com.oak.organization.constant.OrganizationInjectFieldExpressionConstant;
import com.oak.organization.enums.ApprovalStatus;
import com.oak.organization.enums.OrganizationType;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "新增机构")
public class AddOrganizationReq extends ApiBaseReq {

    @Schema(description = "机构名称")
    @NotNull
    private String name;

    @Schema(description = "联系人")
    @NotNull
    private String contacts;

    @Schema(description = "手机号码")
    @NotNull
    @Size(min = 11, max = 12)
    private String mobilePhone;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "区域ID")
    @Size(max = 20)
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

    @Schema(description = "类型")
    @NotNull
    private OrganizationType organizationType;

    @Hidden
    @InjectField(value = OrganizationInjectFieldExpressionConstant.INJECT_ORGANIZATION_ID, condition = "true")
    private Long creatorId;

    @Hidden
    @Schema(description = "审核状态")
    private ApprovalStatus status = ApprovalStatus.AGREE;
}

