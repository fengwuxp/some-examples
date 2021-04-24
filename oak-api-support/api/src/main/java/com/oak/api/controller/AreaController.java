package com.oak.api.controller;

import com.oak.api.services.area.AreaService;
import com.oak.api.services.area.info.AreaInfo;
import com.oak.api.services.area.req.EditAreaReq;
import com.oak.api.services.area.req.QueryAreaReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 区域
 * @author chen
 */
@RestController(value = "AreaController")
@RequestMapping("/area")
@Tag(name = "区域", description = "区域相关")
@Slf4j
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 查询地区列表
     *
     * @param req
     * @return
     */
    @Operation(summary = "查询地区列表", description = "查询地区列表")
    @GetMapping("")
    public ApiResp<Pagination<AreaInfo>> queryArea(QueryAreaReq req) {
        return RestfulApiRespFactory.queryOk(areaService.query(req));
    }

    /**
     * 修改城市信息
     * @param req
     * @return
     */
    @PutMapping(value = "")
    @Operation(summary = "修改城市信息", description = "修改城市信息")
    public ApiResp<Void> edit(@RequestBody EditAreaReq req) {
        return areaService.edit(req);
    }
}
