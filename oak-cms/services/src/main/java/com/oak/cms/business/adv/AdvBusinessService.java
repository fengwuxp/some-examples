package com.oak.cms.business.adv;

import com.oak.cms.business.adv.req.BrowseAdvReq;
import com.oak.cms.business.adv.req.CreateExtendAdvReq;
import com.oak.cms.business.adv.req.QueryAdvExtendReq;
import com.oak.cms.business.adv.req.SetAdvTopReq;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Classname AdvBusinessService
 * @Description 广告管理业务服务
 * @Date 2020/5/28 16:17
 * @Created by 44487
 */
public interface AdvBusinessService {

    /**
     * 创建一个新的广告信息
     * @param req
     * @return
     */
    ApiResp<Long> createAdv(CreateExtendAdvReq req);

    /**
     * 删除一个广告信息
     * @param deleteAdvReq
     * @return
     */
    ApiResp<Void> deleteAdv(DeleteAdvReq deleteAdvReq);

    /**
     * 查询广告信息
     * @param queryAdvExtendReq
     * @return
     */
    ApiResp<Pagination<AdvInfo>> queryAdv(QueryAdvExtendReq queryAdvExtendReq);

    /**
     * 浏览或者是点击广告信息
     * @param browseAdvReq
     * @return
     */
    ApiResp<Void> browseAdvInfo(BrowseAdvReq browseAdvReq);

    /**
     * 在同一类型下相同广告置顶请求
     * @param setAdvTopReq
     * @return
     */
    ApiResp<Void> setAdvTop(SetAdvTopReq setAdvTopReq);

}
