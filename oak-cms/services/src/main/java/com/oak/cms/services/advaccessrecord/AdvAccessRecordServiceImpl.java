package com.oak.cms.services.advaccessrecord;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.AdvAccessRecord;
import com.oak.cms.services.advaccessrecord.info.AdvAccessRecordInfo;
import com.oak.cms.services.advaccessrecord.req.CreateAdvAccessRecordReq;
import com.oak.cms.services.advaccessrecord.req.DeleteAdvAccessRecordReq;
import com.oak.cms.services.advaccessrecord.req.EditAdvAccessRecordReq;
import com.oak.cms.services.advaccessrecord.req.QueryAdvAccessRecordReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 *  广告访问记录服务
 *  2020-5-28 15:15:29
 */
@Service
@Slf4j
public class AdvAccessRecordServiceImpl implements AdvAccessRecordService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateAdvAccessRecordReq req) {

        long codeC = jpaDao.selectFrom(AdvAccessRecord.class)
                .eq("advId",req.getAdvId())
                .eq("ip", req.getIp())
                .eq("accessType",req.getAccessType())
                .count();
        if (codeC > 0) {
            return RestfulApiRespFactory.error("该广告信息已经在该ip地址浏览");
        }

        AdvAccessRecord entity = new AdvAccessRecord();
        BeanUtils.copyProperties(req, entity);

        //设置曝光或者是点击时间
        entity.setCrateTime( new Date() );

        jpaDao.create(entity);


        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditAdvAccessRecordReq req) {


        AdvAccessRecord entity = jpaDao.find(AdvAccessRecord.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("广告访问记录数据不存在");
        }

        UpdateDao<AdvAccessRecord> updateDao = jpaDao.updateTo(AdvAccessRecord.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新广告访问记录失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteAdvAccessRecordReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(AdvAccessRecord.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(AdvAccessRecord.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除广告访问记录");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public AdvAccessRecordInfo findById(Long id) {

        QueryAdvAccessRecordReq queryReq = new QueryAdvAccessRecordReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<AdvAccessRecordInfo> query(QueryAdvAccessRecordReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,AdvAccessRecord.class,AdvAccessRecordInfo.class,req);

    }
}
