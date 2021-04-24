package test.com.oak.member.security.controller;

import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.MemberCreatedManager;
import com.oak.member.security.develop.req.MobileRegisterReq;
import com.oak.member.security.develop.req.OpenIdRegisterReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户发展（注册）相关
 *
 * @author wxup
 */
@RestController
@RequestMapping("/develop")
@Slf4j
@Tag(name = "用户发展（注册）相关服务", description = "用户服务")
public class MemberDevelopController {


    @Autowired
    private MemberCreatedManager memberCreatedManager;


    @PostMapping("/mobile")
    @Operation(summary = "手机号码注册", description = "用户注册")
    public ApiResp<Void> mobilePhoneRegister(@RequestBody  MobileRegisterReq req) {
        MemberDefinition memberDefinition = memberCreatedManager.create(req);
        return RestfulApiRespFactory.ok();
    }

    @PostMapping("/open")
    @Operation(summary = "通过第三方开放平台注册", description = "用户注册")
    public ApiResp<Void> openRegister(@RequestBody OpenIdRegisterReq req) {
        MemberDefinition memberDefinition = memberCreatedManager.create(req);
        return RestfulApiRespFactory.ok();
    }

}
