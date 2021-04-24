package com.oak.member.services.memberdepositcash.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除会员账户提现记录
 *  2020-6-8 23:10:52
 */
@Schema(description = "删除会员账户提现记录")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberDepositCashReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "id集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberDepositCashReq() {
    }

    public DeleteMemberDepositCashReq(Long id) {
        this.id = id;
    }

}
