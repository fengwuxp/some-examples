package com.oak.member.services.memberpersonal.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.service.domain.Desc;


import java.io.Serializable;
import java.util.Date;


/**
* 个人实名信息
* 2020-9-9 11:56:03
*/
@Schema(description ="个人实名信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MemberPersonalInfo implements Serializable {

        @Schema(description = "用户id")
        private Long id;

        @Schema(description = "真实姓名")
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

        @Schema(description = "核验结果，为空表示未核验")
        private Boolean checkResult;

        @Schema(description = "核验结果信息")
        private String checkInfo;

        @Schema(description = "创建日期")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;


}
