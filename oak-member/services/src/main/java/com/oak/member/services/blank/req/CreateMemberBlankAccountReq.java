package com.oak.member.services.blank.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 创建MemberBlankAccount
 * 2020-6-8 10:07:37
 */
@Schema(description = "创建CreateMemberBlankAccountReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberBlankAccountReq extends ApiBaseReq {

    @Schema(description = "提现账户类型")
    @NotNull
    @Size(max = 16)
    private String blankType;

    @Schema(description = "账户关联会员id")
    @NotNull
    private Long memberId;

    @Schema(description = "开户行名称")
    private String bankName;

    @Schema(description = "开户姓名")
    @NotNull
    @Size(max = 32)
    private String accountName;

    @Schema(description = "账号")
    @NotNull
    @Size(max = 32)
    private String accountNumber;

    @Schema(description = "账户预留手机号")
    @Size(max = 12)
    private String memberPhone;


}
