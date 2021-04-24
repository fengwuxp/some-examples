package com.oak.member.services.blank.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 编辑用户银行账号信息表
 * 2020-6-8 10:07:37
 */
@Schema(description = "编辑用户银行账号信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberBlankAccountReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "开户行名称")
    @Update
    private String bankName;

    @Size(max = 32)
    @Schema(description = "开户姓名")
    @Update
    private String accountName;

    @Size(max = 32)
    @Schema(description = "账号")
    @Update
    private String accountNumber;

    @Size(max = 12)
    @Schema(description = "账户预留手机号")
    @Update
    private String memberPhone;


    public EditMemberBlankAccountReq() {
    }

    public EditMemberBlankAccountReq(Long id) {
        this.id = id;
    }
}
