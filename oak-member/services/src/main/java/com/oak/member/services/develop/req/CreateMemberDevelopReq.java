package com.oak.member.services.develop.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.fission.DevelopMode;
import com.oak.member.enums.fission.DevelopType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * 创建MemberDevelop
 * 2020-6-3 21:55:15
 *
 * @author wxup
 */
@Schema(description = "创建CreateMemberDevelopReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberDevelopReq extends ApiBaseReq {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "发展类型")
    @NotNull
    private DevelopType type;

    @Schema(description = "传播方式")
    @NotNull
    private DevelopMode mode;

    @Schema(description = "发展人")
    private Long developId;


}
