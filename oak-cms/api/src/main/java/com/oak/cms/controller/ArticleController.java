package com.oak.cms.controller;

import com.oak.cms.business.article.ArticleBusinessService;
import com.oak.cms.business.article.req.ArticleExtendReq;
import com.oak.cms.business.article.req.InteractionReq;
import com.oak.cms.services.article.ArticleService;
import com.oak.cms.services.article.info.ArticleInfo;
import com.oak.cms.services.article.req.CreateArticleReq;
import com.oak.cms.services.article.req.DeleteArticleReq;
import com.oak.cms.services.article.req.EditArticleReq;
import com.oak.cms.services.articleaction.req.CreateArticleActionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname ArticleController
 * @Description 文章管理
 * @Date 2020/5/31 17:19
 * @Created by 44487
 */

@RestController
@RequestMapping("/v1/article")
@Tag(name = "文章管理", description = "文章管理")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleBusinessService articleBusinessService;

    @Autowired
    private ArticleService articleService;

    /**
     * 文章查询接口
     * @param req
     * @return
     */
    @Operation(summary = "查询文章信息" ,description = "查询文章信息")
    @GetMapping("/query_article")
    public  ApiResp<Pagination<ArticleInfo>> queryArticle(ArticleExtendReq req){
       return  articleBusinessService.queryArticle(req);
    }

    /**
     * 创建文章
     */
    @Operation(summary = "创建文章信息" ,description = "创建文章信息")
    @PostMapping("/create_article")
    public  ApiResp<Long> createArticle( @RequestBody CreateArticleReq createArticleReq){
         return articleBusinessService.createArticle( createArticleReq );
    }

    /**
     * 编辑文章
     */
    @Operation(summary = "编辑文章信息" ,description = "编辑文章信息")
    @PutMapping("/edit_article")
    public  ApiResp<Void> editArticle( @RequestBody EditArticleReq editArticleExtendReq){
        return articleService.edit(editArticleExtendReq);
    }

    /**
     * 删除文章
     */
    @Operation(summary = "删除文章信息" ,description = "删除文章信息")
    @PutMapping("/delete_article")
    public   ApiResp<Void> deleteArticle( @RequestBody DeleteArticleReq deleteArticleReq){
        return articleBusinessService.deleteArticle( deleteArticleReq );
    }


    /**
     * 查看某一个具体的文章信息
     */
    @Operation(summary = "获取一个文章的详细信息" ,description = "获取一个文章的详细信息")
    @GetMapping("/{id}")
    public ApiResp<ArticleInfo> getDetail(  @PathVariable("id") Long id ){
        return RestfulApiRespFactory.ok( articleService.findById( id) );
    }

    /**
     * 文章交互操作
     */
    @Operation(summary = "文章交互" ,description = "文章交互")
    @PutMapping("/article_interactive")
    public  ApiResp<Void > articleInteractive( @RequestBody CreateArticleActionReq createArticleActionReq){
        return articleBusinessService.articleInteractive( createArticleActionReq );
    }

    @Operation(summary = "判断文章的是否进行指定类型的交互" ,description = "判断文章的是否进行指定类型的交互")
    @GetMapping("/determine_articleinteractive")
    public ApiResp<Boolean> determineArticleInteractive(InteractionReq req){
        return articleBusinessService.determineArticleInteractive( req );
    }



}
