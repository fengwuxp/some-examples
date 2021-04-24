//package com.wuxp.security.example.controller.cms;
//
//import com.oak.api.model.PageInfo;
//import com.oak.cms.enums.ArticleActionType;
//import com.oak.cms.management.channel.ChannelManagementService;
//import com.oak.cms.management.channel.req.AddArticleActionReq;
//import com.oak.cms.services.articleaction.ArticleActionService;
//import com.oak.cms.services.articleaction.info.ArticleActionInfo;
//import com.oak.cms.services.articleaction.req.DeleteArticleActionReq;
//import com.oak.cms.services.articleaction.req.EditArticleActionReq;
//import com.oak.cms.services.articleaction.req.QueryArticleActionReq;
//import com.wuxp.api.ApiResp;
//import com.wuxp.api.model.Pagination;
//import com.wuxp.api.model.QueryType;
//import com.wuxp.api.restful.RestfulApiRespFactory;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//
//@RestController
//@RequestMapping("/article_action")
//@Tag(name = "ArticleAction", description = "ArticleAction管理")
//@Slf4j
//public class ArticleActionController {
//
//
//    @Autowired
//    private ArticleActionService articleActionService;
//
//    @Autowired
//    private ChannelManagementService channelManagementService;
//
//    /**
//     * 分页数据
//     *
//     * @param req QueryArticleActionReq
//     * @return ApiResp<Pagination < ArticleActionInfo>>
//     */
//    @GetMapping("/query")
//    @Operation(summary = "查询ArticleAction", description = "ArticleAction")
//    public ApiResp<Pagination<ArticleActionInfo>> query(QueryArticleActionReq req) {
////        return RestfulApiRespFactory.ok(channelManagementService.getArticleActionPages(req));
//        log.debug("接收到参数==>{}", req);
//
//        PageInfo<ArticleActionInfo> pageInfo = PageInfo.newInstance(1, 10, QueryType.QUERY_BOTH);
//
//        ArticleActionInfo actionInfo = new ArticleActionInfo();
//        actionInfo.setActionType(ArticleActionType.COLLECTION);
//        actionInfo.setId(1L);
//        actionInfo.setArticleId(2L);
//        actionInfo.setCrateTime(new Date());
//        actionInfo.setSourceCode("sou");
//        actionInfo.setIp("192.168.0.23");
//        List<ArticleActionInfo> records = new ArrayList<>();
//        records.add(actionInfo);
//        pageInfo.setRecords(records);
//
//        return RestfulApiRespFactory.ok(pageInfo);
//    }
//
//
//    /**
//     * 新增保存
//     *
//     * @param req CreateArticleActionEvt
//     * @return ApiResp
//     */
//    @PostMapping("/create")
//    @Operation(summary = "添加文章互动记录", description = "ArticleAction")
//    public ApiResp<Long> create(AddArticleActionReq req) {
////        return channelManagementService.addArticleAction(req);
//        log.info("接收的数据==>{}", req);
//        return RestfulApiRespFactory.ok(1L);
//    }
//
//
//    //*********************以下暂时不用**************************************
//
//    /**
//     * 详情
//     *
//     * @param id Long
//     */
//    @GetMapping("/{id}")
//    @Operation(summary = "详情ArticleAction", description = "ArticleAction")
//    public ApiResp<ArticleActionInfo> detail(@PathVariable Long id) {
////        return RestfulApiRespFactory.ok(articleActionService.findById(id));
//        log.info("接收的数据==>{}", id);
//        ArticleActionInfo actionInfo = new ArticleActionInfo();
//        actionInfo.setActionType(ArticleActionType.COLLECTION);
//        actionInfo.setId(id);
//        actionInfo.setArticleId(2L);
//        actionInfo.setCrateTime(new Date());
//        actionInfo.setSourceCode("sou");
//        actionInfo.setIp("192.168.0.23");
//        return RestfulApiRespFactory.ok(actionInfo);
//    }
//
//
//    /**
//     * 修改保存
//     */
//    @PutMapping("/edit")
//    @Operation(summary = "编辑ArticleAction", description = "ArticleAction")
//    public ApiResp<Void> edit(EditArticleActionReq req) {
////        return articleActionService.edit(req);
//        log.info("接收的数据==>{}", req);
//        return RestfulApiRespFactory.ok();
//    }
//
//
//    /**
//     * 删除
//     */
//    @GetMapping("/delete")
//    @Operation(summary = "删除ArticleAction", description = "ArticleAction")
//    public ApiResp<Void> delete(DeleteArticleActionReq req) {
//        log.info("接收的数据==>{}", req);
//        return RestfulApiRespFactory.error("删除失败");
//    }
//
//
//
//
//}
