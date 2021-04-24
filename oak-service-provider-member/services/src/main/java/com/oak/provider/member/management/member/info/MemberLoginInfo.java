package com.oak.provider.member.management.member.info;

import com.levin.commons.dao.annotation.Ignore;
import com.oak.provider.member.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author laiy
 * create at 2020-02-17 10:00
 * @Description
 */

@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginInfo implements Serializable {

    private static final long serialVersionUID = 5624226243031658927L;

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "会员类型")
    private String memberType;

    @Schema(description = "会员编号")
    private String no;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "会员昵称")
    private String nickName;

    @Schema(description = "会员姓名")
    private String realName;

    @Schema(description = "性别")
    private Gender gender;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "区域编码")
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    private Boolean idAuth;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "推广二维码")
    private String qrCode;

    @Schema(description = "登录后token")
    @Ignore
    private String token;

    @Schema(description = "token过期时间")
    @Ignore
    private Date tokenExpireDate;

    @Schema(description = "服务提供商的标识")
    private Long serviceProviderId;


    @Schema(description = "是否未设置登录密码")
    private Boolean notPassword = false;

    @Schema(description = "是否未设置支付密码")
    private Boolean notPayPassword = true;

    public String getShowName() {
        if (nickName != null && !nickName.isEmpty()) {
            return nickName;
        } else if (realName != null && !realName.isEmpty()) {
            return realName;
        } else if (mobilePhone != null && mobilePhone.length() == 11) {
            return mobilePhone.substring(0, 3) + "****" + mobilePhone.substring(7);
        }
        return userName;
    }

    public String getFullName() {
        return realName != null
                ? realName + "[" + userName + "]"
                : (nickName != null ? nickName + "[" + userName + "]" : userName);
    }
}
