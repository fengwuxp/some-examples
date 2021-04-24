package com.oak.member.security.authentication;

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
import java.util.Collections;
import java.util.Date;


/**
 * @author wxup
 */
@Schema(description = "用户登录信息")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OakMemberDetails extends User implements PasswordUserDetails {

    @Schema(description = "会员ID")
    private Long id;

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

    @Schema(description = "用于密码加密的盐")
    private transient String cryptoSalt;

    @Schema(description = "客户端渠道号")
    private String clientCode;

    public OakMemberDetails(String username, String password) {
        super(username, password, Collections.emptyList());
    }

    public OakMemberDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, Collections.emptyList());
    }


    @Override
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
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
