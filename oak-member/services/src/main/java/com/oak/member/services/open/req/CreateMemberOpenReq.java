package com.oak.member.services.open.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.logic.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 创建MemberOpen
 * 2020-2-8 20:22:08
 *
 * @author wxup
 */
@Schema(description = "创建CreateMemberOpenReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberOpenReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    @NotNull
    private Long memberId;

    @Schema(description = "平台类型")
    @NotNull
    private OpenType openType;

    @Schema(description = "openId")
    private String openId;

    @Schema(description = "unionId")
    private String unionId;

    @Schema(description = "绑定信息")
    @Size(max = 500)
    private String bindInfo;

    @Schema(description = "到期日期")
    private Date expirationDate;

    @Schema(description = "是否关注")
    private Boolean subscribe;

    @Schema(description = "绑定的渠道")
    @NotNull
    private String bindChannelCode;

}
