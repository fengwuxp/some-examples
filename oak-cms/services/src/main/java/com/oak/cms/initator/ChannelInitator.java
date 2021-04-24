package com.oak.cms.initator;

import com.oak.cms.services.channel.ChannelService;
import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channel.req.CreateChannelReq;
import com.oak.cms.services.channel.req.QueryChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname ChannelInitator
 * @Description 栏目初始化器
 * @Date 2020/5/29 14:44
 * @Created by 44487
 */
public class ChannelInitator extends AbstractBaseInitiator<ChannelInitializeSettings> {

    @Autowired
    private ChannelService channelService;

    @Override
    protected boolean initSingleItem(ChannelInitializeSettings data) {

        //先判断是否存对应的编号
        QueryChannelReq queryChannelReq = new QueryChannelReq();
        queryChannelReq.setCode( data.getCode() );

        ChannelInfo channelInfo = channelService.query(queryChannelReq).getFirst();
        if (  channelInfo == null ){

            CreateChannelReq createChannelReq = new CreateChannelReq();
            BeanUtils.copyProperties( data,createChannelReq );

            //初始化
            ApiResp<Long> apiResp = channelService.create(createChannelReq);

            if( !apiResp.isSuccess() ){
                return apiResp.isSuccess();
            }

            //创建二级栏目
            if( data.getLowerChannel() != null ){
                //创建计数标记
                int count = 0;

                for ( ChannelInitializeSettings lowerChannelSettings : data.getLowerChannel() ) {

                    QueryChannelReq lowerQueryReq = new QueryChannelReq();
                    lowerQueryReq.setCode( lowerChannelSettings.getCode() );

                    ChannelInfo lowerInfo = channelService.query(lowerQueryReq).getFirst();

                    if(lowerInfo == null ){

                        CreateChannelReq lowerChannelReq = new CreateChannelReq();

                        lowerChannelReq.setName(lowerChannelSettings.getName() )
                                .setNextMode(lowerChannelSettings.getNextMode())
                                .setCode( lowerChannelSettings.getCode() )
                                .setParentId( apiResp.getData() );

                        ApiResp<Long> lowerApi = channelService.create(lowerChannelReq);

                        if( lowerApi.isSuccess() ){
                            count++;
                        }
                    }

                }
                return 0 != count;
            }

            return apiResp.isSuccess();

        }else{
            return false;
        }
    }
}
