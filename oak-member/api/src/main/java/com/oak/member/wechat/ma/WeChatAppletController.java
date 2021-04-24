package com.oak.member.wechat.ma;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.oak.member.wechat.ma.info.WxSessionInfo;
import com.oak.member.wechat.ma.info.WxUserInfo;
import com.oak.member.wechat.ma.req.GetWxMaPhoneNumberReq;
import com.oak.member.wechat.ma.req.GetWxMaSessionReq;
import com.oak.member.wechat.ma.req.GetWxMaUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxup
 */
@RestController
@RequestMapping("/we_chat_ma")
@Slf4j
@ConditionalOnBean(WxMaService.class)
@Tag(name = "微信小程序相关服务", description = "微信小程序相关")
@Setter
public class WeChatAppletController implements InitializingBean, BeanFactoryAware {

    private BeanFactory beanFactory;

    private WxMaService wxMaService;

    /**
     * 获取微信小程序会话信息
     *
     * @param req
     * @return
     */
    @PostMapping("/session")
    @Operation(summary = "获取微信小程序会话信息", description = "微信小程序相关")
    public ApiResp<WxSessionInfo> getWxMaSessionInfo(GetWxMaSessionReq req) {


        try {
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(req.getCode());
            WxSessionInfo wxSessionInfo = new WxSessionInfo();
            wxSessionInfo.setOpenId(wxMaJscode2SessionResult.getOpenid())
                    .setUnionId(wxMaJscode2SessionResult.getUnionid())
                    .setSessionKey(wxMaJscode2SessionResult.getSessionKey());
            return RestfulApiRespFactory.ok(wxSessionInfo);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return RestfulApiRespFactory.error("获取微信会话失败");
    }

    /**
     * 获取微信小程序用户信息
     *
     * @param req
     * @return
     */
    @PostMapping("/user")
    @Operation(summary = "获取微信小程序用户信息", description = "微信小程序相关")
    public ApiResp<WxUserInfo> getWxMaUserInfo(GetWxMaUserReq req) {

        try {
            WxUserInfo wxUserInfo = new WxUserInfo();
            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(req.getSessionKey(), req.getEncryptedData(), req.getIvStr());
            BeanUtils.copyProperties(userInfo, wxUserInfo);
            return RestfulApiRespFactory.ok(wxUserInfo);
        } catch (Exception e) {
            log.error("解密数据失败：", e);
        }
        return RestfulApiRespFactory.error("解密数据失败");
    }

    /**
     * 获取微信小程序用户手机号
     *
     * @param req
     * @return
     */
    @PostMapping("/phone_number")
    @Operation(summary = "获取微信小程序用户手机号", description = "微信小程序相关")
    public ApiResp<String> getWxMaPhoneNumber(GetWxMaPhoneNumberReq req) {
        try {
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService()
                    .getPhoneNoInfo(req.getSessionKey(), req.getEncryptedData(), req.getIvStr());
            return RestfulApiRespFactory.ok(phoneNoInfo.getPhoneNumber());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解密数据失败：", e);
        }
        return RestfulApiRespFactory.error("解密数据失败");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.wxMaService == null) {
            this.wxMaService = this.beanFactory.getBean(WxMaService.class);
        }
    }
}
