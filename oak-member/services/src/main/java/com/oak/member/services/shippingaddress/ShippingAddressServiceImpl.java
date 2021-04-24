package com.oak.member.services.shippingaddress;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SettingValueHelper;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.services.area.info.AreaInfo;
import com.oak.api.services.infoprovide.InfoProvideService;
import com.oak.member.entities.E_ShippingAddress;
import com.oak.member.entities.ShippingAddress;
import com.oak.member.services.shippingaddress.info.ShippingAddressInfo;
import com.oak.member.services.shippingaddress.req.CreateShippingAddressReq;
import com.oak.member.services.shippingaddress.req.DeleteShippingAddressReq;
import com.oak.member.services.shippingaddress.req.EditShippingAddressReq;
import com.oak.member.services.shippingaddress.req.QueryShippingAddressReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * 收获地址服务
 * 2020-6-11 14:27:51
 *
 * @author chen
 */
@Service
@Slf4j
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final static String MAX_SHIPPING_NUM = "member.max.receive.address";

    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private InfoProvideService infoProvideService;

    @Autowired
    private SettingValueHelper settingValueHelper;


    /***
     *创建收获地址
     * @param req
     * @return
     */
    @Override
    public ApiResp<Long> create(CreateShippingAddressReq req) {

        //条数限制
        int count = (int) jpaDao.selectFrom(ShippingAddress.class)
                .eq(E_ShippingAddress.memberId, req.getMemberId())
                .count();
        int receiveMax = settingValueHelper.getValue(MAX_SHIPPING_NUM, Integer.TYPE, 10);
        if (count > receiveMax) {
            return RestfulApiRespFactory.error("最多允许创建" + receiveMax + "条收货地址");
        }

        // 覆盖默认的收货地址
        tryCoverDefaultAddress(req.getTheDefault(), req.getMemberId());

        ShippingAddress entity = new ShippingAddress();
        BeanUtils.copyProperties(req, entity);
        //查询配送地址详情
        ApiResp<AreaInfo[]> recursiveAreaInfos = infoProvideService.recursiveAreaInfos(req.getAreaId());
        if (!recursiveAreaInfos.isSuccess()) {
            return RestfulApiRespFactory.error(recursiveAreaInfos.getErrorMessage());
        }
        entity.setAreaName(Arrays.stream(recursiveAreaInfos.getData())
                .map(AreaInfo::getName)
                .collect(Collectors.joining()));

        entity.setCreateTime(new Date())
                .setLastUpdateTime(new Date())
                .setDeleted(false)
                .setRecipient(req.getRecipient());
        jpaDao.create(entity);
        return RestfulApiRespFactory.ok(entity.getId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> edit(EditShippingAddressReq req) {

        ShippingAddress entity = jpaDao.find(ShippingAddress.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("收货地址不存在");
        }

        if (!entity.getMemberId().equals(req.getMemberId())) {
            return RestfulApiRespFactory.error("非本人收货地址");
        }

        // 覆盖默认的收货地址
        tryCoverDefaultAddress(req.getTheDefault(), req.getMemberId());

        UpdateDao<ShippingAddress> updateDao = jpaDao.updateTo(ShippingAddress.class).appendByQueryObj(req);
        if (req.getAreaId() != null && !entity.getAreaId().equals(req.getAreaId())) {
            //收货地址变更 查询配送地址详情
            ApiResp<AreaInfo[]> recursiveAreaInfos = infoProvideService.recursiveAreaInfos(req.getAreaId());
            if (!recursiveAreaInfos.isSuccess()) {
                return RestfulApiRespFactory.error(recursiveAreaInfos.getErrorMessage());
            }
            updateDao.set(E_ShippingAddress.areaName, Arrays.stream(recursiveAreaInfos.getData())
                    .map(AreaInfo::getName)
                    .collect(Collectors.joining()));
        }

        boolean r = updateDao.update() > 0;

        if (!r) {
            return RestfulApiRespFactory.error("收货地址编辑失败");
        }

        return RestfulApiRespFactory.ok();
    }


    @Override
    public ApiResp<Void> delete(DeleteShippingAddressReq req) {
        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r = jpaDao.deleteFrom(ShippingAddress.class).appendByQueryObj(req).delete() > 0;

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ShippingAddressInfo findById(Long id) {

        QueryShippingAddressReq queryReq = new QueryShippingAddressReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ShippingAddressInfo> query(QueryShippingAddressReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, ShippingAddress.class, ShippingAddressInfo.class, req);

    }


    @Override
    public ShippingAddressInfo getMemberDefaultShippingAddress(Long memberId) {
        QueryShippingAddressReq req = new QueryShippingAddressReq();
        req.setMemberId(memberId)
                .setTheDefault(true)
                .setQuerySize(1);
        return this.query(req).getFirst();
    }

    /**
     * 尝试覆盖默认的收货地址
     *
     * @param theDefault 是否为默认，需使用对象，防止NPE
     * @param memberId   用户id
     */
    private void tryCoverDefaultAddress(Boolean theDefault, Long memberId) {
        boolean coverDefault = Boolean.TRUE.equals(theDefault);
        if (!coverDefault) {
            return;
        }
        jpaDao.updateTo(ShippingAddress.class)
                .eq(E_ShippingAddress.memberId, memberId)
                .eq(E_ShippingAddress.theDefault, true)
                .set(E_ShippingAddress.theDefault, false)
                .update();
    }

}
