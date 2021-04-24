package com.oak.api.services.infoprovide;


import com.oak.api.services.area.AreaService;
import com.oak.api.services.area.info.AreaInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class InfoProvideServiceImpl implements InfoProvideService {


    @Autowired
    private AreaService areaService;


    @Override
    public ApiResp<AreaInfo[]> recursiveAreaInfos(@NonNull String areaCode) {

        AreaInfo areaInfo = areaService.findById(areaCode);
        if (areaInfo == null) {
            return RestfulApiRespFactory.error("该地区不存在");
        }
        List<AreaInfo> list = new ArrayList<>();
        list.add(areaInfo);
        while (areaInfo.getLevel() > 1) {
            areaInfo = areaService.findById(areaInfo.getParentId());
            if (areaInfo == null) {
                break;
            }
            list.add(areaInfo);
        }
        Collections.reverse(list);
        return RestfulApiRespFactory.ok(list.toArray(new AreaInfo[0]));
    }

}
