package com.oak.member.services.account.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * 创建MemberAccount
 * 2020-6-8 10:22:51
 */
@Schema(description = "创建CreateMemberAccountReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberAccountReq extends ApiBaseReq {

    @Schema(description = "用户id")
    @NotNull
    private Long memberId;

}
