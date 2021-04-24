package com.oak.cms.services.channel;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.Channel;
import com.oak.cms.entities.E_Channel;
import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channel.req.CreateChannelReq;
import com.oak.cms.services.channel.req.DeleteChannelReq;
import com.oak.cms.services.channel.req.EditChannelReq;
import com.oak.cms.services.channel.req.QueryChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Stack;


/**
 *  栏目服务
 *  2020-5-28 15:25:31
 */
@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateChannelReq req) {

        long channelName = jpaDao.selectFrom(Channel.class)
                .eq(E_Channel.name, req.getName())
                .count();
        if (channelName > 0) {
            return RestfulApiRespFactory.error("名称已被使用！");
        }


        long codeC = jpaDao.selectFrom(Channel.class)
                .eq(E_Channel.code, req.getCode())
                .count();
        if (codeC > 0) {
            return RestfulApiRespFactory.error("栏目编号已被使用！");
        }

        Channel entity = new Channel();
        BeanUtils.copyProperties(req, entity);

        Date date = new Date();
        entity.setUpdateTime(date);
        entity.setCrateTime(date);

        //判断是否存在上级
        ChannelInfo parentChannel = null;
        if( req.getParentId() != null ){
            parentChannel = findById(req.getParentId());
        }

        //设置栏目的层级排序 以及层级关系
        if( parentChannel != null ){
            //有上级
            entity.setLevel( parentChannel.getLevel()+1 );
            entity.setLevelPath( parentChannel.getLevelPath()+entity.getLevel()+"#" );

        }else {
            // 无上级 （顶层父级）
            entity.setLevel(1 );
            entity.setLevelPath( "#1#" );
        }

        jpaDao.create(entity);

        //变更层级关系
        EditChannelReq editChannelReq = new EditChannelReq();
        editChannelReq.setId( entity.getId() );

        if ( parentChannel != null  ){
            //有上级
            editChannelReq.setPath( parentChannel.getPath()+entity.getId()+"#" );
        }else{
            //无上级
            editChannelReq.setPath("#"+entity.getId()+"#");
        }
        //编辑信息
        edit( editChannelReq );

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditChannelReq req) {


        if (!StringUtils.isEmpty(req.getCode())) {
            long codeC = jpaDao.selectFrom(Channel.class)
                    .eq("code", req.getCode())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (codeC > 0) {
                return  RestfulApiRespFactory.error("栏目编号已被使用");
            }
        }

        Channel entity = jpaDao.find(Channel.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("栏目数据不存在");
        }

        UpdateDao<Channel> updateDao = jpaDao.updateTo(Channel.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新栏目失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteChannelReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Channel.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Channel.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除栏目");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ChannelInfo findById(Long id) {

        QueryChannelReq queryReq = new QueryChannelReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ChannelInfo> query(QueryChannelReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,Channel.class,ChannelInfo.class,req);

    }

    @Override
    public ChannelInfo queryByCode(String code) {

        if( StringUtils.isBlank( code ) ){
            return null;
        }

        QueryChannelReq queryChannelReq = new QueryChannelReq();
        queryChannelReq.setCode( code );

        ChannelInfo channelInfo = query(queryChannelReq).getFirst();

        return channelInfo;
    }



    @Override
    public ApiResp<Void> articleNumberChange(Long channelId, boolean isIncrease) {

        //获取改栏目具体信息
        ChannelInfo channelInfo = findById(channelId);

        if( channelInfo == null ){
            return RestfulApiRespFactory.error("指定对应的栏目信息不存在");
        }

        //该栏目 有可能是父类 因此 当字类栏目增加文章数量 ，对应的父类文章数量也需要增加，减少一篇文章也是如此
        String[] idPathArray = channelInfo.getPath().split("#");

        //创建一个栈
        Stack<Long> idStack = new Stack<>();
        for (String id : idPathArray) {
            if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
                idStack.push(Long.valueOf(id));
            }
        }

        try {
            recursiveOperation(idStack, isIncrease);
        } catch (Exception e) {
            return RestfulApiRespFactory.error("文章栏目数量变更失败");
        }

        return RestfulApiRespFactory.ok();
    }

    /**
     * 递归操作
     *
     * @param idStack
     * @param isIncrease
     */
    private void recursiveOperation(Stack<Long> idStack, boolean isIncrease) {

        if (idStack.empty()) {
            return;
        } else {

            //弹出一个元素
            Long id = idStack.pop();

            ChannelInfo channelInfo = findById(id);

            EditChannelReq editChannelReq = new EditChannelReq();
            editChannelReq.setId(id)
                    .setNumberOfArticles(channelInfo.getNumberOfArticles() + (isIncrease ? 1 : -1));

            //防止负数
            if( editChannelReq.getNumberOfArticles() < 0 ){
                editChannelReq.setNumberOfArticles(0);
            }

            ApiResp<Void> apiResp = edit(editChannelReq);
            if (!apiResp.isSuccess()) {
                throw new RuntimeException("文章栏目数量编辑操作失败");
            }

            //递归操作
            recursiveOperation(idStack, isIncrease);
        }

    }



}
