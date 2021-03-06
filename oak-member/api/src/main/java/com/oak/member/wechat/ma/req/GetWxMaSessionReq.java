package com.oak.member.wechat.ma.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取微信用户信息
 *
 * @author wxup
 */
@Data
public class GetWxMaSessionReq extends ApiBaseReq {

    @Schema(description = "调用凭证")
    @NotNull
    private String code;

}
