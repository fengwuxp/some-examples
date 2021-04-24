package com.oak.api.services.infoprovide;


import com.oak.api.services.area.info.AreaInfo;
import com.wuxp.api.ApiResp;
import lombok.NonNull;

/**
 * 数据维护服务
 *
 * @author wxup
 */
public interface InfoProvideService {


    /**
     * 递归的获取地区信息，直到获取到最上层
     *
     * @param areaCode
     * @return 地区层级从低到高排序
     */
    ApiResp<AreaInfo[]> recursiveAreaInfos(@NonNull String areaCode);


}
