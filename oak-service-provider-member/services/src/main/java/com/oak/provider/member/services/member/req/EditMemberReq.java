package com.oak.provider.member.services.member.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.constant.MemberApiContextInjectExprConstant;
import com.oak.provider.member.enums.Gender;
import com.oak.provider.member.enums.MemberVerifyStatus;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑会员信息
 *  2020-2-6 15:32:43
 */
@Schema(description = "编辑会员信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberReq extends ApiBaseReq {

    @Schema(description = "会员ID", hidden = true)
    @NotNull
    @Eq(require = true)
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long id;

    @Size(max = 11)
    @Schema(description = "手机号")
    @Update
    private String mobilePhone;

    @Schema(description = "Email")
    @Update
    private String email;

    @Size(max = 20)
    @Schema(description = "用户名")
    @Update
    private String userName;

    @Size(max = 100)
    @Schema(description = "会员昵称")
    @Update
    private String nickName;

    @Size(max = 50)
    @Schema(description = "区域编码")
    @Update
    private String areaId;

    @Schema(description = "区域名称")
    @Update
    private String areaName;

    @Size(max = 200)
    @Schema(description = "地址")
    @Update
    private String address;

    @Schema(description = "性别")
    @Update
    private Gender gender;

    @Schema(description = "生日")
    @Update
    private Date birthday;

    @Size(max = 100)
    @Schema(description = "标签")
    @Update
    private String tags;

    @Schema(description = "手机认证")
    @Update
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    @Update
    private Boolean idAuth;

    @Schema(description = "头像URL")
    @Update
    private String avatarUrl;

    @Schema(description = "注册时间")
    @Update
    private Date regDateTime;

    @Size(max = 32)
    @Schema(description = "注册时间")
    @Update
    private String regSource;

    @Schema(description = "会员类型")
    @Update
    private String memberType;

    @Schema(description = "是否未设置登录密码")
    @Update
    private Boolean notPassword;

    @Schema(description = "是否未设置支付密码")
    @Update
    private Boolean notPayPassword;

    @Schema(description = "封闭到期时间")
    @Update
    private Date frozenDate;

    @Schema(description = "审核状态")
    @Update
    private MemberVerifyStatus verify;

    @Schema(description = "推广二维码")
    @Update
    private String qrCode;

    @Schema(description = "名称")
    @Update
    private String name;

    @Schema(description = "排序代码")
    @Update
    private Integer orderCode;

    @Schema(description = "是否允许")
    @Update
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @Update
    private Boolean editable;

    @Schema(description = "更新时间")
    @Update
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @Update
    private String remark;

    public EditMemberReq() {
    }

    public EditMemberReq(Long id) {
        this.id = id;
    }
}
