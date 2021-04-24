package com.oak.member.services.member.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author wxup
 */
@Schema(description = "通过用户名手机号码和或者邮箱来检查用户是否存在")
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CheckMemberExistReq extends ApiBaseReq {

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "电子邮箱")
    private String email;
}
