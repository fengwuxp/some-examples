package com.oak.member.services.account.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author wxup
 */
@Schema(description = "更新账号状态和vip等级信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class UpdateAccountStatusReq extends ApiBaseReq {

    @Schema(description = "用户id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "账户状态")
    @Update
    private AccountStatus status;

    @Schema(description = "用户会员vip级别")
    @Update
    private VipGrade vipGrade;
}
