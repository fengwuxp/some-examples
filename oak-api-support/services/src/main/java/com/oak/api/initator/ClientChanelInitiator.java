package com.oak.api.initator;


import com.oak.api.services.client.ClientChannelService;
import com.oak.api.services.client.info.ClientChannelInfo;
import com.oak.api.services.client.req.CreateClientChannelReq;
import com.oak.api.services.client.req.QueryClientChannelReq;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * 渠道表初始化器
 *
 * @author wxup
 */
@Slf4j
public class ClientChanelInitiator extends AbstractBaseInitiator<CreateClientChannelReq> {


    @Autowired
    private ClientChannelService clientChannelService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void init() {
        super.init();
    }

    @Override
    protected boolean initSingleItem(CreateClientChannelReq data) {
        QueryClientChannelReq queryClientChannelReq = new QueryClientChannelReq(data.getCode());
        queryClientChannelReq.setQueryType(QueryType.QUERY_NUM);
        Pagination<ClientChannelInfo> page = clientChannelService.query(queryClientChannelReq);
        if (page.isEmpty()) {
            String clientCode = clientChannelService.create(data);
            if (log.isDebugEnabled()) {
                log.debug("创建渠道【" + data + "】-> {} ,{}", data, clientCode);
            }
            return true;
        }
        return false;
    }
}
