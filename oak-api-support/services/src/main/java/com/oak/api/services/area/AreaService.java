package com.oak.api.services.area;

import com.oak.api.services.area.info.AreaInfo;
import com.oak.api.services.area.req.EditAreaReq;
import com.oak.api.services.area.req.QueryAreaReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 地区信息服务
 * 2020-6-16 10:25:17
 */
public interface AreaService {
    String AREA_CACHE_NAME = "AREA_CACHE";

//    ApiResp<String> create(CreateAreaReq req);


    ApiResp<Void> edit(EditAreaReq req);


    AreaInfo findById(String id);

    /**
     * 通过第三方区域编码查找地区信息
     *
     * @param areaCode  区域编码
     * @param thirdCode 第三方区域编码
     * @return
     */
    AreaInfo findByThirdCode(@NotNull String areaCode, @NotNull String thirdCode);


    Pagination<AreaInfo> query(QueryAreaReq req);

    /**
     * 获取可用的地区列表
     * @return
     */
    ApiResp<List<AreaInfo>> getAreaList();

}
