package com.oak.cms.business.article;

import cn.hutool.core.date.DateUtil;
import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.oak.api.model.PageInfo;
import com.oak.cms.business.article.req.ArticleExtendReq;
import com.oak.cms.business.article.req.InteractionReq;
import com.oak.cms.entities.Article;
import com.oak.cms.entities.ArticleAction;
import com.oak.cms.entities.Channel;
import com.oak.cms.services.article.ArticleService;
import com.oak.cms.services.article.info.ArticleInfo;
import com.oak.cms.services.article.req.CreateArticleReq;
import com.oak.cms.services.article.req.DeleteArticleReq;
import com.oak.cms.services.article.req.EditArticleReq;
import com.oak.cms.services.article.req.QueryArticleReq;
import com.oak.cms.services.articleaction.ArticleActionService;
import com.oak.cms.services.articleaction.req.CreateArticleActionReq;
import com.oak.cms.services.channel.ChannelService;
import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channelarticle.ChannelArticleService;
import com.oak.cms.services.channelarticle.req.CreateChannelArticleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname ArticleBusinessServiceImpl
 * @Description 文章业务方法实现
 * @Date 2020/5/29 14:29
 * @Created by 44487
 * <p>
 * 这是一个通用的文章业务方法
 * 例如 系统协议，用户须知，常见问答都是适用于该通用业务
 */
@Service
@Slf4j
public class ArticleBusinessServiceImpl implements ArticleBusinessService {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ChannelArticleService channelArticleService;

    @Autowired
    private ArticleActionService articleActionService;

    @Autowired
    private JpaDao jpaDao;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ApiResp<Pagination<ArticleInfo>> queryArticle(ArticleExtendReq req) {

        //普通的查询
        QueryArticleReq queryBaseArticleReq = new QueryArticleReq();
        //忽略栏目编号，只通过栏目id查找
        BeanUtils.copyProperties(req, queryBaseArticleReq, "channelCode");
        //设置发布时间
        if (req.getStartPublishTime() != null) {
            queryBaseArticleReq.setMinPublishTime(DateUtil.beginOfDay(req.getStartPublishTime()));
        }
        if (req.getEndPublishTime() != null) {
            queryBaseArticleReq.setMaxPublishTime(DateUtil.endOfDay(req.getEndPublishTime()));
        }

        if (StringUtils.isNotBlank(req.getChannelCode())) {
            //针对栏目号进行查询

            ChannelInfo channelInfo = channelService.queryByCode(req.getChannelCode());
            if (channelInfo == null) {
                return RestfulApiRespFactory.error("请输入正确的栏目编号");
            }

            Long[] channelIds = getChannelIds(channelInfo.getId());

            //设置分页对象
            PageInfo<ArticleInfo> pageInfo = PageInfo.newInstance(req);

            //栏目id 设置为 null
            queryBaseArticleReq.setChannelId(null);
            SelectDao<Article> selectDao = jpaDao.selectFrom(Article.class, "a")
                    .appendByQueryObj(queryBaseArticleReq)
                    .in("channelId", channelIds);

            pageInfo.setTotal(selectDao.count());
            List<ArticleInfo> result = selectDao.page(req.getQueryPage(), req.getQuerySize()).find(ArticleInfo.class);

            //手动设置 channelInfo 信息
            HashMap<String, ChannelInfo> channelMap = new HashMap<>();
            for (ArticleInfo info : result) {
                ChannelInfo channel = channelMap.get(info.getChannelCode());
                if (null == channel) {
                    channel = channelService.findById(info.getChannelId());
                    channelMap.put(info.getChannelCode(),channel);
                    info.setChannelInfo(channel);
                }else{
                    info.setChannelInfo(channel);
                }
            }

            pageInfo.setRecords(result == null ? new ArrayList<>() : result);

            //返回结果
            return RestfulApiRespFactory.ok(pageInfo);

        } else {

            Pagination<ArticleInfo> pagination = articleService.query(queryBaseArticleReq);

            return RestfulApiRespFactory.ok(pagination);
        }

    }


    @Override
    public ApiResp<Long> createArticle(CreateArticleReq req) {


        ApiResp<ChannelInfo> channelInfoApiResp = getChannelInfoByIDOrCode(req.getChannelId(), req.getChannelCode());

        if (!channelInfoApiResp.isSuccess()) {
            return RestfulApiRespFactory.error(channelInfoApiResp.getErrorMessage());
        }
        ChannelInfo channelInfo = channelInfoApiResp.getData();

        //设置初始化值
        req.setChannelId(channelInfo.getId())
                .setChannelCode(channelInfo.getCode());

        //创建文章
        ApiResp<Long> articleApiResp = articleService.create(req);

        if (!articleApiResp.isSuccess()) {
            return RestfulApiRespFactory.error("文章添加失败！");
        }

        CreateChannelArticleReq createChannelArticleReq = new CreateChannelArticleReq();
        createChannelArticleReq.setArticleId(articleApiResp.getData())
                .setChannelId(channelInfo.getId());

        ApiResp<Long> articleChannelApiresp = channelArticleService.create(createChannelArticleReq);

        if (!articleChannelApiresp.isSuccess()) {
            return RestfulApiRespFactory.error("文章栏目关联失败");
        }

        //关联栏目的文章数量+1
        ApiResp<Void> articleNumber = channelService.articleNumberChange(channelInfo.getId(), true);
        if (!articleNumber.isSuccess()) {
            return RestfulApiRespFactory.error("栏目文章添加失败");
        }

        return RestfulApiRespFactory.ok();
    }


    @Override
    public ApiResp<Void> deleteArticle(DeleteArticleReq deleteArticleReq) {

        ArticleInfo articleInfo = articleService.findById(deleteArticleReq.getId());

        if (articleInfo == null) {
            return RestfulApiRespFactory.error("删除失败，该文章对象不存在，请重新检查");
        }

        //删除一个文章信息 - 需要先删除文章 ，删除文章交互
        articleActionService.deleteByArticleId(articleInfo.getId());

        //删除文章与栏目关联内容
        channelArticleService.deleteByArticleId(articleInfo.getId());

        //关联栏目的文章数量 -1
        channelService.articleNumberChange(articleInfo.getChannelId(), false);

        return articleService.delete(deleteArticleReq);
    }

    @Override
    public ApiResp<Void> articleInteractive(CreateArticleActionReq req) {

        //找到对应的文章信息
        ArticleInfo articleInfo = articleService.findById(req.getArticleId());

        if (articleInfo == null) {
            return RestfulApiRespFactory.error("该文章对象不存在！");
        }

        //创建文章浏览记录
        ApiResp<Long> articleApiResp = articleActionService.create(req);

        if (!articleApiResp.isSuccess()) {
            return RestfulApiRespFactory.error("文章互动操作失败");
        }

        //文章编辑对象
        EditArticleReq editArticleReq = new EditArticleReq();
        editArticleReq.setId(articleInfo.getId());

        switch (req.getActionType()) {

            case VIEW:
                editArticleReq.setViews(articleInfo.getViews() == null ? 1 : articleInfo.getViews() + 1);
                break;

            case LIKE:
                editArticleReq.setLikes(articleInfo.getLikes() == null ? 1 : articleInfo.getLikes() + 1);
                break;

            case COMMENT:
                editArticleReq.setComments(articleInfo.getComments() == null ? 1 : articleInfo.getComments() + 1);
                break;

            case COLLECTION:
                editArticleReq.setCollections(articleInfo.getCollections() == null ? 1 : articleInfo.getCollections() + 1);
                break;

            case SHARE:
                editArticleReq.setShares(articleInfo.getShares() == null ? 1 : articleInfo.getShares() + 1);
                break;
        }

        ApiResp<Void> editApiresp = articleService.edit(editArticleReq);
        return editApiresp;
    }

    @Override
    public ApiResp<Boolean> determineArticleInteractive(InteractionReq req) {

        long count = jpaDao.selectFrom(ArticleAction.class, "ac")
                .eq("articleId", req.getArticleId())
                .eq("actionType", req.getActionType())
                .eq("ip", req.getIp()).count();

        if (count > 0) {
            return RestfulApiRespFactory.ok();
        } else {
            return RestfulApiRespFactory.error(MessageFormat.format("此文章未进行该类型:{0}操作", req.getActionType().toString()));
        }
    }


    /**
     * 获取栏目的id
     *
     * @param id
     */
    private Long[] getChannelIds(Long id) {
        List<Channel> channelList = jpaDao.selectFrom(Channel.class, "c")
                .contains("path", "#" + String.valueOf(id) + "#")
                .find(Channel.class);

        List<Long> collect = channelList.stream().map(Channel::getId).collect(Collectors.toList());
        Long[] ids = new Long[0];
        ids = collect.toArray(ids);

        return ids;
    }


    /**
     * 通过id和code找到对应的栏目
     *
     * @return
     */
    private ApiResp<ChannelInfo> getChannelInfoByIDOrCode(Long channelId, String code) {

        //栏目编号不能为空
        ChannelInfo channelInfo = null;
        if (StringUtils.isNotBlank(code)) {
            channelInfo = channelService.queryByCode(code);
        } else if (channelId != null) {
            channelInfo = channelService.findById(channelId);
        } else {
            return RestfulApiRespFactory.error("栏目ID或者是栏目编号不能输入空值");
        }

        if (channelInfo == null) {
            return RestfulApiRespFactory.error("请输入正确的栏目ID或者是栏目编号");
        }

        return RestfulApiRespFactory.ok(channelInfo);
    }

}
