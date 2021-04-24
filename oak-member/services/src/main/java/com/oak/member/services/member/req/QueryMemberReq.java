package com.oak.member.services.member.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.logic.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author wxup
 */
@Schema(description = "查询会员信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryMemberReq extends ApiBaseQueryReq {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "会员编号")
    private String no;

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "会员昵称")
    private String nickName;

    @Schema(description = "区域编码")
    private String areaId;

    @Schema(description = "性别")
    private Gender gender;

    @Schema(description = "最小生日")
    @Gte("birthday")
    private Date minBirthday;

    @Schema(description = "最大生日")
    @Lte("birthday")
    private Date maxBirthday;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    private Boolean idAuth;

    @Schema(description = "是否未设置登录密码")
    private Boolean notPassword;

    @Schema(description = "是否未设置支付密码")
    private Boolean notPayPassword;

    @Schema(description = "最小封闭到期时间")
    @Gte("frozenDate")
    private Date minFrozenDate;

    @Schema(description = "最大封闭到期时间")
    @Lte("frozenDate")
    private Date maxFrozenDate;

    @Schema(description = "审核状态")
    private MemberVerifyStatus verify;

    @Schema(description = "加载帐户信息")
    @Fetch(value = "memberAccount", condition = "#_val==true")
    private Boolean loadMemberAccount;

    @Schema(description = "加载安全信息")
    @Fetch(value = "memberSecure", condition = "#_val==true")
    private Boolean loadMemberSecure;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "推广码")
    private String inviteCode;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "绑定开放平台的类型")
    @Ignore
    private OpenType openType;

    @Schema(description = "未绑定的平台的类型")
    @Ignore
    private OpenType notOpenType;

    public QueryMemberReq() {
    }

    public QueryMemberReq(Long id) {
        this.id = id;
    }
}
