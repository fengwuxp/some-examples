package com.oak.app.controller;

import com.oak.app.services.appversion.AppVersionService;
import com.oak.app.services.appversion.info.AppVersionInfo;
import com.oak.app.services.appversion.req.CreateAppVersionReq;
import com.oak.app.services.appversion.req.EditAppVersionReq;
import com.oak.app.services.appversion.req.QueryAppVersionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenXiaoBin
 * on 2020-05-25
 */

@RestController
@RequestMapping("/app_version")
@Slf4j
@Tag(name = "版本管理", description = "版本管理")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @GetMapping("/query")
    @Operation(summary = "查询所有的APP版本", description = "查询所有的APP版本")
    public ApiResp<Pagination<AppVersionInfo>> query(QueryAppVersionReq req) {
        return RestfulApiRespFactory.ok(appVersionService.query(req));
    }

    @PostMapping("/create")
    @Operation(summary = "创建APP版本", description = "创建APP版本")
    public ApiResp<Long> create(@RequestBody CreateAppVersionReq req) {
        return appVersionService.create(req);
    }

    @PutMapping("/edit")
    @Operation(summary = "编辑APP版本", description = "编辑APP版本")
    public ApiResp<Void> edit(@RequestBody EditAppVersionReq req) {
        return appVersionService.edit(req);
    }


}
