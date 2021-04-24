package com.oak.member.dto;

import com.oak.member.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxup
 */
@Schema(description = "会员信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
public class MemberDTO implements Serializable {

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

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "区域编码")
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "推广码")
    private String inviteCode;

    @Schema(description = "创建时间")
    protected Date createTime;
}
