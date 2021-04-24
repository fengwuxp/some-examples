package com.oak.organization.management.user.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 刷新token
 */
@Schema(description = "刷新token")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class RefreshAdminUserTokenReq extends ApiBaseReq {

    @Schema(description = "token")
    @NotNull
    private String token;

}
