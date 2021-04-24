package com.oak.member.services.memberlog.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建MemberLog
 *  2020-6-3 14:07:02
 * @author wxup
 */
@Schema(description = "创建CreateMemberLogReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberLogReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    @NotNull
    private Long memberId;

    @Schema(description = "会员显示名称")
    @NotNull
    @Size(max = 32)
    private String showName;

    @Schema(description = "操作类型")
    @NotNull
    @Size(max = 16)
    private String type;

    @Schema(description = "IP")
    @Size(max = 32)
    private String ip;

    @Schema(description = "操作者")
    @NotNull
    @Size(max = 32)
    private String operator;

    @Schema(description = "操作描述")
    @NotNull
    @Size(max = 256)
    private String description;

    @Schema(description = "操作日期")
    @NotNull
    private Date operatingTime;

}
