package com.oak.rbac.security;

import com.wuxp.security.authenticate.PasswordUserDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.beans.Transient;
import java.util.Collection;
import java.util.Date;


/**
 * @author wxup
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OakUser extends User implements PasswordUserDetails {


    private static final long serialVersionUID = -6068509382111627921L;

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobilePhone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "用于密码加密的盐")
    private transient String cryptoSalt;

    @Schema(description = "是否超管理")
    private Boolean root;

    @Schema(description = "登录token")
    private String token;

    @Schema(description = "token有效的毫秒数，客户端使用")
    private long effectiveMilliseconds;

    @Schema(description = "token失效时间，服务端判断使用")
    private Date tokenExpired;

    @Schema(description = "refresh token")
    private String refreshToken;

    @Schema(description = "refresh token有效的毫秒数，客户端使用")
    private long refreshEffectiveMilliseconds;

    @Schema(description = "refresh token失效时间，服务端判断使用")
    private Date refreshTokenExpired;

    @Schema(description = "客户端渠道号")
    private String clientCode;


    public OakUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public OakUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Transient
    @Override
    public String getCryptoSalt() {
        return cryptoSalt;
    }


    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.cryptoSalt = null;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setEffectiveMilliseconds(long effectiveMilliseconds) {
        this.effectiveMilliseconds = effectiveMilliseconds;
    }

    @Override
    public void setTokenExpired(Date tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public void setRefreshTokenExpired(Date refreshTokenExpired) {
        this.refreshTokenExpired = refreshTokenExpired;
    }

    @Override
    public void setRefreshEffectiveMilliseconds(long refreshEffectiveMilliseconds) {
        this.refreshEffectiveMilliseconds = refreshEffectiveMilliseconds;
    }
}
