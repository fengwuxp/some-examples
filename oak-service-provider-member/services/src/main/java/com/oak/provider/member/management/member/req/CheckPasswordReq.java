package com.oak.provider.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-17 16:55
 * @Description
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CheckPasswordReq extends ApiBaseReq {


    @Schema(description = "用户ID")
    @NotNull
    private Long uid;

    @Schema(description = "密码")
    @NotNull
    private String password;

}
