package com.oak.member.services.blank.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 用户银行账号信息表
* 2020-6-8 10:07:37
*/
@Schema(description ="用户银行账号信息表")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MemberBlankAccountInfo implements Serializable {

        @Schema(description = "id")
        private Long id;

        @Schema(description = "提现账户类型")
        private String blankType;

        @Schema(description = "账户关联会员id")
        private Long memberId;

        @Schema(description = "开户行名称")
        private String bankName;

        @Schema(description = "开户姓名")
        private String accountName;

        @Schema(description = "账号")
        private String accountNumber;

        @Schema(description = "账户预留手机号")
        private String memberPhone;

        @Schema(description = "是否删除")
        private Boolean deleted;

        @Schema(description = "日志日期")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;


}
