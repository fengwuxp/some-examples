package com.oak.member.services.promotion.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.logic.enums.PromotionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;



/**
 * 创建MemberPromotion
 * 2020-6-3 21:29:08
 *
 * @author wxup
 */
@Schema(description = "创建CreateMemberPromotionReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberPromotionReq extends ApiBaseReq  {

    @Schema(description = "推广用户的Id")
    @NotNull
    private Long fromMemberId;

    @Schema(description = "推广类型")
    @NotNull
    private PromotionType type;

    @Schema(description = "被推广用户的no（来源注册时）")
    @NotNull
    private String toMemberNo;

    @Schema(description = "被推广用户ID（来源注册时）")
    @NotNull
    private Long toMemberId;


}
