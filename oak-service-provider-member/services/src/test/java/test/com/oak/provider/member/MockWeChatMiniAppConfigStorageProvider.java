package test.com.oak.provider.member;

import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.fengwuxp.miniapp.multiple.WeChatMaConfigProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockWeChatMiniAppConfigStorageProvider implements WeChatMaConfigProvider {


    @Override
    public WxMaConfig getWxMpConfigStorage(String appId) {
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(appId);
        wxMaConfig.setSecret(appId);
        return wxMaConfig;
    }
}
