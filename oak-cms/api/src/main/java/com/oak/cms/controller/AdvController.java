package com.oak.cms.controller;

import com.oak.cms.business.adv.AdvBusinessService;
import com.oak.cms.business.adv.req.BrowseAdvReq;
import com.oak.cms.business.adv.req.CreateExtendAdvReq;
import com.oak.cms.business.adv.req.QueryAdvExtendReq;
import com.oak.cms.business.adv.req.SetAdvTopReq;
import com.oak.cms.services.adv.AdvService;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname AdvController
 * @Description 广告信息初始化
 * @Date 2020/5/29 9:16
 * @Created by 44487
 */

@RestController
@RequestMapping("/v1/advmanagement")
@Tag(name = "后台广告管理", description = "广告管理")
@Slf4j
public class AdvController {

    @Autowired
    private AdvBusinessService advBusinessService;

    @Autowired
    private AdvService advService;

    @Operation(summary = "获取单个广告详细信息" ,description = "获取单个广告详细信息")
    @GetMapping("/{id}")
    public ApiResp<AdvInfo> queryAdvDetail( @PathVariable("id") Long id ){
        return RestfulApiRespFactory.ok(advService.findById( id ) );
    }

    /**
     * 创建一个新的广告信息
     * @param req
     * @return
     */
    @Operation(summary = "创建广告信息" ,description = "创建广告信息")
    @PostMapping("/create_adv")
    public  ApiResp<Long> createAdv( @RequestBody CreateExtendAdvReq req){
       return advBusinessService.createAdv(req);
    }

    /**
     * 编辑广告信息
     * @return
     */
    @Operation(summary = "修改广告信息" ,description = "修改广告信息")
    @PutMapping("/edit_adv")
    public ApiResp<Void> editAdv( @RequestBody EditAdvReq editAdvReq){
        return advService.edit( editAdvReq );
    }

    /**
     * 删除一个广告信息
     * @param deleteAdvReq
     * @return
     */
    @Operation(summary = "删除广告信息" ,description = "删除广告信息")
    @PutMapping("/delete_adv")
    public  ApiResp<Void> deleteAdv( @RequestBody DeleteAdvReq deleteAdvReq){
        return advBusinessService.deleteAdv( deleteAdvReq );
    }

    /**
     * 查询广告信息
     * @param queryAdvExtendReq
     * @return
     */
    @Operation(summary = "查询广告信息" ,description = "查询广告信息")
    @GetMapping("/query_adv")
    public  ApiResp<Pagination<AdvInfo>> queryAdv(QueryAdvExtendReq queryAdvExtendReq){
        return advBusinessService.queryAdv( queryAdvExtendReq );
    }

    /**
     * 浏览或者是点击广告信息
     * @param browseAdvReq
     * @return
     */
    @Operation(summary = "浏览/点击广告信息" ,description = "浏览/点击广告信息")
    @PutMapping("/browse_adv")
    public  ApiResp<Void> browseAdvInfo( @RequestBody BrowseAdvReq browseAdvReq){
        return advBusinessService.browseAdvInfo( browseAdvReq );
    }

    @Operation(summary = "设置一个广告置顶" ,description = "设置一个广告置顶")
    @PutMapping("/set_adv_top")
    public ApiResp<Void> setAdvTop(@RequestBody SetAdvTopReq editAdvReq){
        return advBusinessService.setAdvTop(editAdvReq);
    }

}
