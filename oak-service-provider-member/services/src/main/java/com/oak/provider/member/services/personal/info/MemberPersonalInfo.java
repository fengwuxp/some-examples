package com.oak.provider.member.services.personal.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 个人实名信息
 * 2020-3-5 17:08:11
 */
@Schema(description = "个人实名信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MemberPersonalInfo implements Serializable {

    private static final long serialVersionUID = 2028101441661201125L;
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "身份证号")
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

    @Schema(description = "核验结果")
    private Boolean checkResult;

    @Schema(description = "核验结果信息")
    private String checkInfo;

    @Schema(description = "登记日期")
    private Date createTime;


}
