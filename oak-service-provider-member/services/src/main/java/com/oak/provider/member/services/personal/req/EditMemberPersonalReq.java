package com.oak.provider.member.services.personal.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑个人实名信息
 *  2020-3-5 17:08:12
 */
@Schema(description = "编辑个人实名信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberPersonalReq extends ApiBaseReq {

    @Schema(description = "ID", hidden = true)
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    @Eq(require = true)
    private Long id;

    @Schema(description = "姓名")
    @Update
    private String name;

    @Size(max = 18)
    @Schema(description = "身份证号")
    @Update
    private String idNumber;

    @Schema(description = "证件生效日期")
    @Update
    private Date beginDate;

    @Schema(description = "证件截止日期")
    @Update
    private Date endDate;

    @Schema(description = "正面身份证照")
    @Update
    private String facePic;

    @Schema(description = "反面身份证照")
    @Update
    private String backPic;

    @Schema(description = "手持身份证照")
    @Update
    private String handPic;

    @Schema(description = "核验人员", hidden = true)
    @Update
    private String checkOperator;

    @Schema(description = "核验日期", hidden = true)
    @Update
    private Date checkTime;

    @Schema(description = "核验结果", hidden = true)
    @Update
    private Boolean checkResult;

    @Schema(description = "核验结果信息", hidden = true)
    @Update
    private String checkInfo;

    public EditMemberPersonalReq() {
    }

    public EditMemberPersonalReq(Long id) {
        this.id = id;
    }
}
