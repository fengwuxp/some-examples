package com.oak.member.services.shippingaddress;

import com.oak.member.services.shippingaddress.info.ShippingAddressInfo;
import com.oak.member.services.shippingaddress.req.CreateShippingAddressReq;
import com.oak.member.services.shippingaddress.req.DeleteShippingAddressReq;
import com.oak.member.services.shippingaddress.req.EditShippingAddressReq;
import com.oak.member.services.shippingaddress.req.QueryShippingAddressReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 收获地址服务
 * 2020-6-11 14:27:51
 *
 * @author chen
 */
public interface ShippingAddressService {

    /**
     * 创建收货地址
     *
     * @param req
     * @return
     */
    ApiResp<Long> create(CreateShippingAddressReq req);


    ApiResp<Void> edit(EditShippingAddressReq req);


    ApiResp<Void> delete(DeleteShippingAddressReq req);


    ShippingAddressInfo findById(Long id);


    Pagination<ShippingAddressInfo> query(QueryShippingAddressReq req);


    /**
     * 获取用户默认的收货地址
     *
     * @param memberId
     * @return
     */
    ShippingAddressInfo getMemberDefaultShippingAddress(Long memberId);

}
