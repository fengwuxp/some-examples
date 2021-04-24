package com.oak.member.services.blank.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除用户银行账号信息表
 *  2020-6-8 10:07:37
 */
@Schema(description = "删除用户银行账号信息表")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberBlankAccountReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "id集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberBlankAccountReq() {
    }

    public DeleteMemberBlankAccountReq(Long id) {
        this.id = id;
    }

}
