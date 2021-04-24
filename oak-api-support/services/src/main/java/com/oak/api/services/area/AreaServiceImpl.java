package com.oak.api.services.area;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.entities.system.Area;
import com.oak.api.entities.system.E_Area;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.services.area.info.AreaInfo;
import com.oak.api.services.area.req.EditAreaReq;
import com.oak.api.services.area.req.QueryAreaReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 地区信息服务
 * 2020-6-16 10:25:17
 */
@Service
@Slf4j
public class AreaServiceImpl implements AreaService {


    @Autowired
    private JpaDao jpaDao;


    @Override
    public Pagination<AreaInfo> query(QueryAreaReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, Area.class, AreaInfo.class, req);
    }

    /**
     * 获取可用的地区列表
     *
     * @return
     */
    @Override
    public ApiResp<List<AreaInfo>> getAreaList() {
        List<AreaInfo> areas = jpaDao.selectFrom(Area.class)
                .eq(E_Area.status, true)
                .eq(E_Area.level, 1)
                .find(AreaInfo.class);
        return RestfulApiRespFactory.ok(areas);
    }


    @Cacheable(value = AREA_CACHE_NAME,
            key = "'ID_'+#areaId",
            unless = "#result==null")
    @Override
    public AreaInfo findById(@NotNull String areaId) {
        return this.query(new QueryAreaReq().setId(areaId)).getFirst();
    }

    @Caching(cacheable = {
            @Cacheable(value = AREA_CACHE_NAME,
                    key = "'ID_'+#req.areaCode",
                    unless = "#result==null")
    })
    @Override
    public AreaInfo findByThirdCode(@NotNull String areaCode, @NotNull String thirdCode) {

        return jpaDao.selectFrom(Area.class)
                .eq(E_Area.id, areaCode)
                .eq(E_Area.thirdCode, thirdCode)
                .findOne(AreaInfo.class, 4);
    }

    @Caching(evict = {
            @CacheEvict(value = AREA_CACHE_NAME, key = "'ID_'+#req.id")
    })
    @Override
    public ApiResp<Void> edit(EditAreaReq req) {

        Area entity = jpaDao.find(Area.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("Area数据不存在");
        }
        UpdateDao<Area> updateDao = jpaDao.updateTo(Area.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新Area失败");
        }

        return RestfulApiRespFactory.ok();
    }

}
