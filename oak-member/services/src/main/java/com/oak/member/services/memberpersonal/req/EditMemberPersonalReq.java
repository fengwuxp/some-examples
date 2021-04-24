package com.oak.member.services.memberpersonal.req;

import com.levin.commons.dao.annotation.update.Update;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑个人实名信息
 *  2020-9-9 11:56:03
 */
@Schema(description = "编辑个人实名信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberPersonalReq extends ApiBaseReq {

    @Schema(description = "用户id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "真实姓名")
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

    @Schema(description = "核验人员")
    @Update
    private String checkOperator;

    @Schema(description = "核验日期")
    @Update
    private Date checkTime;

    @Schema(description = "核验结果，为空表示未核验")
    @Update
    private Boolean checkResult;

    @Schema(description = "核验结果信息")
    @Update
    private String checkInfo;

    @Schema(description = "更新时间")
    @Update
    private Date lastUpdateTime;

    public EditMemberPersonalReq() {
    }

    public EditMemberPersonalReq(Long id) {
        this.id = id;
    }
}
