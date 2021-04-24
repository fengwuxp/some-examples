package com.oak.wechat.publish.services.service;

import com.oak.wechat.publish.services.dto.WxOpenConfigEntity;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;

/**
 * @Classname IComponentConfigProvider
 * @Description TODO
 * @Date 2020/3/17 19:09
 * @Created by 44487
 */
public interface IComponentConfigProvider {

    WxOpenConfigStorage getConfigByDomain(String domain);

    WxOpenConfigEntity getConfigEntityByDomain(String domain);
}
