package com.oak.member.services.memberpersonal.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建MemberPersonal
 *  2020-9-9 11:56:03
 */
@Schema(description = "创建CreateMemberPersonalReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberPersonalReq extends ApiBaseReq {

    @Schema(description = "真实姓名")
    @NotNull
    private String name;

    @Schema(description = "身份证号")
    @NotNull
    @Size(max = 18)
    private String idNumber;

    @Schema(description = "证件生效日期")
    private Date beginDate;

    @Schema(description = "证件截止日期")
    private Date endDate;

    @Schema(description = "正面身份证照")
    private String facePic;

    @Schema(description = "反面身份证照")
    private String backPic;

    @Schema(description = "手持身份证照")
    private String handPic;

    @Schema(description = "核验人员")
    private String checkOperator;

    @Schema(description = "核验日期")
    private Date checkTime;

    @Schema(description = "核验结果，为空表示未核验")
    private Boolean checkResult;

    @Schema(description = "核验结果信息")
    private String checkInfo;

    @Schema(description = "更新时间")
    @NotNull
    private Date lastUpdateTime;

}
