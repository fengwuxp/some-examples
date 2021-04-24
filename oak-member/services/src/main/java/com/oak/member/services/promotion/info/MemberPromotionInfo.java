package com.oak.member.services.promotion.info;

import com.levin.commons.service.domain.Desc;
import com.oak.member.logic.enums.PromotionType;
import com.oak.member.services.member.info.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户推广信息
 * 2020-6-3 21:29:08
 */
@Schema(description = "用户推广信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"memberInfo",})
public class MemberPromotionInfo implements Serializable {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "推广用户的Id")
    private Long fromMemberId;

    @Desc(value = "", code = "member")
    @Schema(description = "推广用户")
    private MemberInfo memberInfo;

    @Schema(description = "推广类型")
    private PromotionType type;

    @Schema(description = "被推广用户的no（来源注册时）")
    private String toMemberNo;

    @Schema(description = "被推广用户ID（来源注册时）")
    private Long toMemberId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;



}
