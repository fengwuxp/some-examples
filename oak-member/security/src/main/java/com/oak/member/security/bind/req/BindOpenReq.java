package com.oak.member.security.bind.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.logic.bind.BindOpenModel;
import com.oak.member.logic.enums.OpenType;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * @author wxup
 */
@Schema(description = "创建CreateMemberOpenReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BindOpenReq extends ApiBaseReq implements BindOpenModel {

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

    @Schema(description = "是否关注")
    private Boolean subscribe;

    @Schema(description = "绑定的渠道")
    @NotNull
    @InjectField(INJECT_CHANNEL_CODE)
    private String bindChannelCode;
}
