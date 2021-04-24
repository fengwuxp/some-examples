package com.oak.member.services.memberlog.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 会员日志
* 2020-6-3 14:07:02
*/
@Schema(description ="会员日志")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MemberLogInfo implements Serializable {

        @Schema(description = "id")
        private Long id;

        @Schema(description = "会员ID")
        private Long memberId;

        @Schema(description = "会员显示名称")
        private String showName;

        @Schema(description = "操作类型")
        private String type;

        @Schema(description = "IP")
        private String ip;

        @Schema(description = "操作者")
        private String operator;

        @Schema(description = "操作描述")
        private String description;

        @Schema(description = "操作日期")
        private Date operatingTime;

        @Schema(description = "创建时间")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;


}
