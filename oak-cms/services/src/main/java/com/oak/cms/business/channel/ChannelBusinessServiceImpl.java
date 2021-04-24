package com.oak.cms.business.channel;

import com.levin.commons.dao.JpaDao;
import com.oak.cms.business.channel.req.CreateChannelExtendReq;
import com.oak.cms.business.channel.req.QueryChannelExtendReq;
import com.oak.cms.entities.Article;
import com.oak.cms.entities.Channel;
import com.oak.cms.services.channel.ChannelService;
import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channel.req.CreateChannelReq;
import com.oak.cms.services.channel.req.DeleteChannelReq;
import com.oak.cms.services.channel.req.QueryChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname ChannelBusinessServiceImpl
 * @Description 专栏 业务实现方法
 * @Date 2020/5/29 14:36
 * @Created by 44487
 */

@Service
@Slf4j
public class ChannelBusinessServiceImpl implements ChannelBusinessService {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Pagination<ChannelInfo>> queryChannel(QueryChannelExtendReq req) {

        //获取父类的栏目code
        ChannelInfo parentChannelInfo = null;
        if (StringUtils.isNotBlank(req.getParentCode()) && req.getParentId() == null) {
            parentChannelInfo = channelService.queryByCode(req.getParentCode());
        }

        if (parentChannelInfo != null) {
            req.setParentId(parentChannelInfo.getId());
        }

        //复制属性
        QueryChannelReq baseChannelReq = new QueryChannelReq();
        BeanUtils.copyProperties(req, baseChannelReq);

        return RestfulApiRespFactory.ok(channelService.query(baseChannelReq));
    }

    @Override
    public ApiResp<Long> createChannel(CreateChannelExtendReq req) {

        ChannelInfo parentChannel = channelService.queryByCode(req.getParentCode());
        if (parentChannel != null && req.getParentId() == null) {
            req.setParentId(parentChannel.getId());
        }

        if (StringUtils.isBlank(req.getCode())) {
            req.setCode(RandomStringUtils.randomAlphabetic(12));
        }

        //复制属性
        CreateChannelReq createChannelReq = new CreateChannelReq();
        BeanUtils.copyProperties(req, createChannelReq);

        return channelService.create(createChannelReq);
    }

    /**
     * 删除一个栏目信息
     *
     * @param deleteChannelReq
     * @return 一个栏目有下级栏目或者是说 栏目关联文章信息，怎不能被删除
     */
    @Override
    public ApiResp<Void> deleteChannel(DeleteChannelReq deleteChannelReq) {

        long articleCount = jpaDao.selectFrom(Article.class, "a")
                .eq("channelId", deleteChannelReq.getId())
                .count();

        if (articleCount > 0) {
            return RestfulApiRespFactory.error("该栏目下存在相关文章信息不能进行删除");
        }

        //判断是否存在下级栏目
        long channelCount = jpaDao.selectFrom(Channel.class, "c")
                .contains("path", "#"+String.valueOf(deleteChannelReq.getId())+"#")
                .count();

        if (channelCount > 1) {
            //超过1以上，就是存在下级
            return RestfulApiRespFactory.error("该栏目下存在下级栏目分类，不能进行删除");
        }

        return channelService.delete(deleteChannelReq);
    }

}
