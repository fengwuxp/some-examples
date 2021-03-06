package com.oak.member.services.secure.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author wxup
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(name = "用户登录失败计数")
public class LoginFailInfo implements Serializable {

    private static final long serialVersionUID = 5868994827208918317L;

    @Schema(name = "用户id")
    private Long memberId;

    @Schema(name = "登录失败计数器")
    private Integer count;

    @Schema(name = "起始锁定时间")
    private Long lockTime;

    @Schema(name = "密码输错时间")
    private Long failTime;

}
