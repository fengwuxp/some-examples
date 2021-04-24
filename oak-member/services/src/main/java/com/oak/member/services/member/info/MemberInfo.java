package com.oak.member.services.member.info;

import com.levin.commons.service.domain.Desc;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.secure.info.MemberSecureInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Date;


/**
 * 会员信息
 * 2020-2-6 15:32:42
 *
 * @author wxup
 */
@Schema(description = "会员信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"memberAccountInfo", "memberSecureInfo"})
public class MemberInfo implements MemberDefinition {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "会员编号")
    private String no;

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "会员昵称")
    private String nickName;

    @Schema(description = "会员真实姓名")
    private String name;

    @Schema(description = "性别")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(description = "生日")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    private Boolean idAuth;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "区域编码")
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "注册来源")
    private String channelCode;

    @Schema(description = "是否未设置登录密码")
    private Boolean notPassword = false;

    @Schema(description = "是否未设置支付密码")
    private Boolean notPayPassword = true;

    @Schema(description = "账号冻结期时间")
    private Date frozenDate;

    @Schema(description = "审核状态")
    private MemberVerifyStatus verify;

    @Schema(description = "推广码")
    private String inviteCode;

    @Schema(description = "是否删除")
    protected Boolean deleted = false;

    @Schema(description = "是否启用")
    protected Boolean enabled = true;

    @Schema(description = "创建时间")
    protected Date createTime;

    @Schema(description = "更新时间")
    protected Date lastUpdateTime;

    @Desc(value = "", code = "memberAccount")
    @Schema(description = "帐户信息")
    private MemberAccountInfo memberAccountInfo;

    @Desc(value = "", code = "memberSecure")
    @Schema(description = "安全信息")
    private MemberSecureInfo memberSecureInfo;


    @Transient
    public String getShowName() {
        if (nickName != null && !nickName.isEmpty()) {
            return nickName;
        } else if (name != null && !name.isEmpty()) {
            return name;
        } else if (mobilePhone != null && mobilePhone.length() == 11) {
            return MessageFormat.format("{0}****{1}", mobilePhone.substring(0, 3), mobilePhone.substring(7));
        }
        return userName;
    }
}
