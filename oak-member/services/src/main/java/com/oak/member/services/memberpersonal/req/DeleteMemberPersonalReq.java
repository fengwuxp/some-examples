package com.oak.member.services.memberpersonal.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;

/**
 *  删除个人实名信息
 *  2020-9-9 11:56:03
 */
@Schema(description = "删除个人实名信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberPersonalReq extends ApiBaseReq {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户id集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberPersonalReq() {
    }

    public DeleteMemberPersonalReq(Long id) {
        this.id = id;
    }

}
