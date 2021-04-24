package com.oak.member.services.secure.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建MemberSecure
 *  2020-2-6 16:00:47
 */
@Schema(description = "创建CreateMemberSecureReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberSecureReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "登录密码")
    @Size(max = 32)
    @NotNull
    private String loginPassword;

    @Schema(description = "支付密码")
    @Size(max = 32)
    @NotNull
    private String payPassword;



}
