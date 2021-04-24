package test.com.oak.cms.management.article;

import com.oak.cms.business.article.ArticleBusinessService;
import com.oak.cms.business.article.req.ArticleExtendReq;
import com.oak.cms.constant.ChannelInitializeSettingsConstant;
import com.oak.cms.enums.*;
import com.oak.cms.services.article.ArticleService;
import com.oak.cms.services.article.info.ArticleInfo;
import com.oak.cms.services.article.req.CreateArticleReq;
import com.oak.cms.services.article.req.DeleteArticleReq;
import com.oak.cms.services.articleaction.req.CreateArticleActionReq;
import com.oak.cms.services.channel.ChannelService;
import com.oak.cms.services.channel.req.CreateChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import test.com.oak.cms.OakApplicationTest;

import java.text.ParseException;

/**
 * @Classname ArticleTest
 * @Description 文章测试
 * @Date 2020/5/29 17:28
 * @Created by 44487
 */

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class ArticleTest {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleBusinessService articleBusinessService;

    @Test
    public void initChannel(){

        //创建栏目
        CreateChannelReq channelReq = new CreateChannelReq();

        channelReq.setCode(ChannelInitializeSettingsConstant.COMMON_PROBLEM_CODE)
                .setName("系统常见问题")
                .setNextMode(ChannelNextMode.SUB);

        ApiResp<Long> apiResp = channelService.create(channelReq);

        Assert.isTrue( apiResp.isSuccess(), "操作失败");
    }

    @Test
    public void createChannel(){

        //创建栏目
        CreateChannelReq channelReq = new CreateChannelReq();

        channelReq.setCode(RandomStringUtils.randomAlphabetic(5))
                .setParentId(1L)
                .setName("返点问题")
                .setNextMode(ChannelNextMode.SUB);

        ApiResp<Long> apiResp = channelService.create(channelReq);

        Assert.isTrue( apiResp.isSuccess(), "操作失败");
    }

    @Test
    public void createArticle(){

        CreateArticleReq createArticleReq = new CreateArticleReq();
        createArticleReq.setChannelId(60001L)
                .setChannelCode("QPnOT")
                .setTitle("提现问题A")
                .setCoverMode(CoverMode.NONE)
                .setContentType(ArticleContentType.HTML)
                .setContent("提现问题AAA")
                .setStatus(ArticleStatus.WAIT)
                .setRemark("备注");

        ApiResp<Long> apiResp = articleService.create(createArticleReq);

        Assert.isTrue( apiResp.isSuccess(),"操作失败" );

    }

    @Test
    public void queryArticle() throws ParseException {

        ArticleExtendReq articleExtendReq = new ArticleExtendReq();
       // ChannelInitializeSettingsConstant.COMMON_PROBLEM_CODE
        articleExtendReq.setChannelCode("XXXXX_TEST"); //设置栏目编号
        articleExtendReq.setStatus( ArticleStatus.PASS )
                .setQuerySize(1);//设置状态
     //   articleExtendReq.setTitle( "用户协议" );
        //设置发布时间
//        articleExtendReq.setStartPublishTime(DateUtil.parseDate("2020-5-29"))
//                .setEndPublishTime(DateUtil.parseDate("2020-5-29"));

        ApiResp<Pagination<ArticleInfo>> paginationApiResp = articleBusinessService.queryArticle(articleExtendReq);

        Assert.isTrue( paginationApiResp.isSuccess(),"操作失败" );


    }


    /**
     *60002	general.user.agreement.code
     *常见协议
     *    《用户协议》
     *    《返点说明》
     *    《游你定挂团报名须知及注意事项》
     *
     */


    /**
     * 创建文章
     */
    @Test
    public void createArticleA( ){

        CreateArticleReq createArticleReq = new CreateArticleReq();
        createArticleReq.setChannelCode("INSTRUCTIONS_AND_PRECAUTIONS")
                .setTitle("游你定挂团报名须知及注意事项")
                .setCoverMode(CoverMode.NONE)
                .setContentType(ArticleContentType.TEXT)
                .setContent("百度消息服务所有权及经营权为百度网讯科技有限公司（以下简称“百度公司”）所有。" +
                        "用户在自愿开始使用百度消息服务之前，必须仔细阅读并接受本服务条款。一经激活百度消息服务功能，则视为对本服务全部条款的认知和接受。")
                .setStatus(ArticleStatus.PASS)
                .setRemark("备注");

        ApiResp<Long> apiResp = articleBusinessService.createArticle(createArticleReq);

        Assert.isTrue( apiResp.isSuccess(),"操作失败" );

    }

    /**
     * 编辑文章
     */
    @Test
    public void editArticle( ){

//        EditArticleExtendReq editArticleExtendReq = new EditArticleExtendReq();
//
//        editArticleExtendReq.setId(30001L)
//                .setContent("测试内容ZZZZZ");
//
//        ApiResp<Void> apiResp = articleBusinessService.editArticle(editArticleExtendReq);
//
//        Assert.isTrue(apiResp.isSuccess(),"操作失败" );

    }

    /**
     * 删除文章
     */
    @Test
    public void deleteArticle( ){
        DeleteArticleReq deleteArticleReq = new DeleteArticleReq();
        deleteArticleReq.setId( 5L );

        ApiResp<Void> apiResp = articleBusinessService.deleteArticle(deleteArticleReq);

        Assert.isTrue(apiResp.isSuccess(),"操作失败" );

    }


    /**
     * 查看某一个具体的文章信息
     */
    public void getDetail(   ){

    }

    /**
     * 文章交互操作
     */
    @Test
    public void  articleInteractive( ){
        CreateArticleActionReq createArticleActionReq = new CreateArticleActionReq();
        createArticleActionReq.setArticleId(60003L)
                .setActionType(ArticleActionType.VIEW )
                .setSourceCode("手机客户端")
                .setIp("127.0.0.1");
        ApiResp<Void> apiResp = articleBusinessService.articleInteractive(createArticleActionReq);

        Assert.isTrue( apiResp.isSuccess(),"操作失败" );

    }



}
