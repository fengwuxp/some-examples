package com.oak.member.api.controller;


import com.oak.member.dto.MemberDTO;
import com.oak.member.mapstruct.MemberApiDtoConverter;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static com.oak.member.constant.MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR;

/**
 * @author wxup
 */
@RestController
@RequestMapping("/member")
@Slf4j
@Tag(name = "用户信息相关服务", description = "用户服务")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "用户信息")
    public ApiResp<MemberDTO> getMember(@InjectField(value = INJECT_MEMBER_ID_EXPR, condition = "true") @NotNull Long memberId) {
        MemberInfo memberInfo = memberService.findById(memberId);
        if (memberInfo == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }

        return RestfulApiRespFactory.ok(MemberApiDtoConverter.MAPPER.memberDTO(memberInfo));
    }
}
