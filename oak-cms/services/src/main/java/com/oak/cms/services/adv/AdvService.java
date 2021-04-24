package com.oak.cms.services.adv;

import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  广告信息服务
 *  2020-5-28 15:02:29
 */
public interface AdvService {


    ApiResp<Long> create(CreateAdvReq req);


    ApiResp<Void> edit(EditAdvReq req);


    ApiResp<Void> delete(DeleteAdvReq req);


    AdvInfo findById(Long id);


    Pagination<AdvInfo> query(QueryAdvReq req);

//    /**
//     * 通过指定广告位id获取到对应的广告数量信息
//     * @param advPositionId
//     * @return
//     */
//    Long queryAdvNumberByAdvPositionId( Long advPositionId );

}
