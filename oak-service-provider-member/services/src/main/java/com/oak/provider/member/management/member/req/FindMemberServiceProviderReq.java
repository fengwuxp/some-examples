package com.oak.provider.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.constant.MemberApiContextInjectExprConstant;
import com.oak.provider.member.enums.OpenType;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * @author laiy
 * create at 2020-03-02 14:58
 * @Description
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FindMemberServiceProviderReq extends ApiBaseReq {

    @Schema(name = "openType", description = "开放平台类型")
    @NotNull
    private OpenType openType;

    @Schema(description = "微信appid")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_WECHAT_APPID)
    private String wxAppId;

}
