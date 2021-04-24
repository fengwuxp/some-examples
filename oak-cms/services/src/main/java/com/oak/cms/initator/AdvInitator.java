package com.oak.cms.initator;

import com.oak.cms.services.adv.AdvService;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
import com.oak.cms.services.advposition.AdvPositionService;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

/**
 * @Classname AdvInitator
 * @Description 广告信息以及广告位初始化
 * @Date 2020/5/26 14:44
 * @Created by 44487
 */
@Slf4j
public class AdvInitator extends AbstractBaseInitiator<AdvInitializeSettings> {

    @Autowired
    private AdvService advService;

    @Autowired
    private AdvPositionService advPositionService;

    @Override
    protected boolean initSingleItem(AdvInitializeSettings data) {

        //先查询是否存在
        QueryAdvPositionReq queryAdvPositionReq = new QueryAdvPositionReq();
        queryAdvPositionReq.setCode( data.getCreateAdvPositionReq().getCode() );

        AdvPositionInfo advPositionInfo = advPositionService.query(queryAdvPositionReq).getFirst();
        if ( advPositionInfo == null ){
            //创建广告位信息
            ApiResp<Long> apiResp = advPositionService.create(data.getCreateAdvPositionReq());

            advPositionInfo = advPositionService.findById( apiResp.getData() );

            if( log.isDebugEnabled() ){
                log.info(MessageFormat.format("初始化广告位置：{0}",advPositionInfo.getName()));
            }
        }

        //创建广告列表信息
        int j = 0;
        if( data.getAdvReqList() != null && data.getAdvReqList().size()>0 ){
            //有值
            for (   CreateAdvReq createAdvReq : data.getAdvReqList()  ){

                QueryAdvReq queryAdvReq = new QueryAdvReq();
                queryAdvReq.setTitle(createAdvReq.getTitle())
                        .setApId( advPositionInfo.getId() );

                AdvInfo first = advService.query(queryAdvReq).getFirst();
                if( first != null ){
                    continue;
                }

                createAdvReq.setApId( advPositionInfo.getId() );
                //创建广告信息
                advService.create( createAdvReq );
                j++;
            }
        }

        return j != 0;
    }
}
