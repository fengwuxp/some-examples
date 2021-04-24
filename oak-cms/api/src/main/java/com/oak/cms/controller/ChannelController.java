package com.oak.cms.controller;

import com.oak.cms.business.channel.ChannelBusinessService;
import com.oak.cms.business.channel.req.CreateChannelExtendReq;
import com.oak.cms.business.channel.req.QueryChannelExtendReq;
import com.oak.cms.services.channel.ChannelService;
import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channel.req.DeleteChannelReq;
import com.oak.cms.services.channel.req.EditChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname ChannelController
 * @Description 文章栏目管理
 * @Date 2020/5/31 17:28
 * @Created by 44487
 */
@RestController
@RequestMapping("/v1/channel")
@Tag(name = "文章栏目管理", description = "文章栏目管理")
@Slf4j
public class ChannelController {

    @Autowired
    private ChannelBusinessService channelBusinessService;

    @Autowired
    private ChannelService channelService;

    /**
     * 查询
     */
    @Operation(summary = "查询栏目列表信息" ,description = "查询栏目列表信息")
    @GetMapping("/query_channel")
    public  ApiResp<Pagination<ChannelInfo>> queryChannel(QueryChannelExtendReq queryChannelExtendReq){
        return channelBusinessService.queryChannel( queryChannelExtendReq );
    }

    /**
     * 添加
     */
    @Operation(summary = "创建文章栏目信息" ,description = "创建文章栏目信息")
    @PostMapping("/create_channel")
    public  ApiResp<Long> createChannel( @RequestBody CreateChannelExtendReq createChannelExtendReq){
        return channelBusinessService.createChannel( createChannelExtendReq );
    }

    /**
     * 修改
     */
    @Operation(summary = "编辑文章栏目信息" ,description = "编辑文章栏目信息")
    @PutMapping("/edit_channel")
    public   ApiResp<Void> editChannel( @RequestBody EditChannelReq req){
        return channelService.edit(req);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除文章栏目信息" ,description = "删除文章栏目信息")
    @PutMapping("/delete_channel")
    public  ApiResp<Void> deleteChannel(@RequestBody DeleteChannelReq deleteChannelReq){
        return channelBusinessService.deleteChannel( deleteChannelReq );
    }


    /**
     * 详细信息
     */
    @Operation(summary = "获取一个文章详细信息" ,description = "获取一个文章详细信息")
    @GetMapping("/{id}")
    public  ApiResp<ChannelInfo> getDetail( @PathVariable("id") Long id){
        return RestfulApiRespFactory.ok( channelService.findById( id) );
    }


}
