package com.oak.member.services.open.req;

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
import java.util.Date;


/**
 * 编辑会员绑定信息
 * 2020-2-8 20:22:08
 */
@Schema(description = "编辑会员绑定信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberOpenReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "unionId")
    @Update
    private String unionId;

    @Size(max = 500)
    @Schema(description = "绑定信息")
    @Update
    private String bindInfo;

    @Schema(description = "到期日期")
    @Update
    private Date expirationDate;

    @Schema(description = "是否关注")
    @Update
    private Boolean subscribe;


    public EditMemberOpenReq() {
    }

    public EditMemberOpenReq(Long id) {
        this.id = id;
    }
}
