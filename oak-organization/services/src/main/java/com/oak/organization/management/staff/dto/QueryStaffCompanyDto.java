package com.oak.organization.management.staff.dto;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Classname QueryStaffCompanyDto
 * @Description 业务员查询对象
 * @Date 2020/3/24 16:49
 * @Created by 44487
 */
@Schema(description = "查询业务员归属代理商")
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class QueryStaffCompanyDto extends ApiBaseQueryReq {

    @Schema(name = "staffId", description = "指定职员id")
    private Long staffId;

    @Schema(name = "type", description = "公司类型")
    private String type;

    @Schema(name = "status", description = "公司状态")
    private String status;

}
