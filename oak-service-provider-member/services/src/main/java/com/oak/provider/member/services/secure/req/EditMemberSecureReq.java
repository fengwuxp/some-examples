package com.oak.provider.member.services.secure.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑MemberSecure
 *  2020-2-6 16:00:47
 */
@Schema(description = "编辑MemberSecure")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberSecureReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    //@Schema(description = "加密key")
    //@Update
    //private String pwdKey;
    //
    //@Size(max = 16)
    //@Size(min = 16 , max = 16)
    //@Schema(description = "动态口令安全密钥")
    //@Update
    //private String totpSecret;

    //@Schema(description = "开启二次验证")
    //@Update
    //private Boolean totpEnabled;

    @Size(max = 128)
    @Schema(description = "登录密码")
    @Update
    private String loginPassword;

    @Size(max = 128)
    @Schema(description = "支付密码")
    @Update
    private String payPassword;

    @Schema(description = "登录次数")
    @Update
    private Integer loginTimes;

    @Schema(description = "最后登录时间")
    @Update
    private Date lastLoginTime;

    @Size(max = 16)
    @Schema(description = "最后登录IP")
    @Update
    private String lastLoginIp;

    @Schema(description = "登录密码设置日期")
    @Update
    private Date loginPwdUpdateTime;

    @Schema(description = "支付密码设置日期")
    @Update
    private Date payPwdUpdateTime;

    @Schema(description = "需要更改登录密码")
    @Update
    private Boolean changeLoginPassword;

    @Schema(description = "需要修改支付密码")
    @Update
    private Boolean changePayPassword;

    @Schema(description = "最后登录密码失败时间")
    @Update
    private Date lastLoginFailTime;

    @Schema(description = "登录密码失败数")
    @Update
    private Integer loginPwdFail;

    @Schema(description = "最后登录密码锁定时间")
    @Update
    private Date lastLoginFailLockTime;

    @Schema(description = "最后支付密码失败时间")
    @Update
    private Date lastPayFailTime;

    @Schema(description = "支付密码失败数")
    @Update
    private Integer payPwdFail;

    @Schema(description = "最后支付密码锁定时间")
    @Update
    private Date lastPayFailLockTime;

    public EditMemberSecureReq() {
    }

    public EditMemberSecureReq(Long id) {
        this.id = id;
    }
}
