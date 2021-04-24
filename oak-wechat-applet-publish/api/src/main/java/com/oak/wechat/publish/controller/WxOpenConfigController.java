package com.oak.wechat.publish.controller;


import com.oak.wechat.publish.services.constant.WxOpenConfigConstant;
import com.oak.wechat.publish.services.wxopenconfig.MiniprogramReleaseService;
import com.oak.wechat.publish.services.wxopenconfig.WxOpenConfigService;
import com.oak.wechat.publish.services.wxopenconfig.dto.MiniProgramQueryAuditResultDto;
import com.oak.wechat.publish.services.wxopenconfig.info.WxOpenConfigInfo;
import com.oak.wechat.publish.services.wxopenconfig.req.CreateWxOpenConfigReq;
import com.oak.wechat.publish.services.wxopenconfig.req.DeleteWxOpenConfigReq;
import com.oak.wechat.publish.services.wxopenconfig.req.EditWxOpenConfigReq;
import com.oak.wechat.publish.services.wxopenconfig.req.QueryWxOpenConfigReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("WxOpenConfigController")
@RequestMapping("/v1/wxopenconfig")
@Tag(name = "组织管理小程序", description = "组织具体管理相对应的小程序配置信息")
@Slf4j
public class WxOpenConfigController {


    @Autowired
    private WxOpenConfigService wxOpenConfigService;

    @Autowired
    private MiniprogramReleaseService miniprogramReleaseService;

    /**
     * 分页数据
     *
     * @param req QueryWxOpenConfigReq
     * @return ApiResp<Pagination < WxOpenConfigInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询WxOpenConfig", description = "查看具体的小程序配置信息")
    public ApiResp<Pagination<WxOpenConfigInfo>> query(QueryWxOpenConfigReq req) {
        return RestfulApiRespFactory.ok(wxOpenConfigService.query(req));
    }

    /**
     * 新增保存
     *
     * @param req CreateWxOpenConfigEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建WxOpenConfig", description = "添加小程序配置信息")
    //@ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<Long> create(CreateWxOpenConfigReq req) {
        return wxOpenConfigService.create(req);
    }

    /**
     * 详情
     *
     * @param id Long
     */
    @GetMapping("/{id}")
    @Operation(summary = "详情WxOpenConfig", description = "查看小程序配置细节")
    public ApiResp<WxOpenConfigInfo> detail(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(wxOpenConfigService.findById(id));
    }

    /**
     * 修改保存
     */
    @PutMapping("/edit")
    @Operation(summary = "编辑WxOpenConfig", description = "修改小程序配置信息")
    public ApiResp<Void> edit(EditWxOpenConfigReq req) {
        return wxOpenConfigService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除WxOpenConfig", description = "删除小程序配置信息")
    public ApiResp<Void> delete(DeleteWxOpenConfigReq req) {
        return wxOpenConfigService.delete(req);
    }

    @GetMapping("/applet_publish")
    @Operation(summary = "发布小程序", description = "发布小程序")
    public ApiResp<String> appletPublish(@InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) @RequestParam("organizationId") String organizationId) throws WxErrorException, WxErrorException {
        return miniprogramReleaseService.miniProgramRelease(organizationId);
    }

    @GetMapping("/query_applet_audit_result")
    @Operation(summary = "查询小程序审核结果", description = "查询一个服务商的小程序审核结果")
    public ApiResp<MiniProgramQueryAuditResultDto> queryAppletAuditResult(@InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) @RequestParam("organizationId") String organizationId) throws WxErrorException {
        return miniprogramReleaseService.queryMiniProgramAuditResult(organizationId);
    }


    @GetMapping("/query_applet_last_submit_audit_result")
    @Operation(summary = "查询小程序最后提交审核结果", description = "查询一个服务商小程序最后一次提交审核结果")
    public ApiResp<MiniProgramQueryAuditResultDto> queryAppletLastSubmitAuditResult(@InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) @RequestParam("organizationId") String organizationId) throws WxErrorException {
        return miniprogramReleaseService.queryMiniProgramAuditResultLastSubmit(organizationId);
    }


}
