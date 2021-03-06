package com.oak.provider.member.services.accountlog.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  查找会员账户信息日志
 *  2020-2-18 14:06:41
 */
@Schema(description = "查找会员账户信息日志")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindMemberAccountLogReq extends ApiBaseReq {

    @Schema(description = "日志ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
