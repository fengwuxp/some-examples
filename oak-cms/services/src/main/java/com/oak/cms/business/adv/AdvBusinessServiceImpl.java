package com.oak.cms.business.adv;

import com.oak.cms.business.adv.req.BrowseAdvReq;
import com.oak.cms.business.adv.req.CreateExtendAdvReq;
import com.oak.cms.business.adv.req.QueryAdvExtendReq;
import com.oak.cms.business.adv.req.SetAdvTopReq;
import com.oak.cms.enums.AdvAccessType;
import com.oak.cms.services.adv.AdvService;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
import com.oak.cms.services.advaccessrecord.AdvAccessRecordService;
import com.oak.cms.services.advaccessrecord.req.CreateAdvAccessRecordReq;
import com.oak.cms.services.advposition.AdvPositionService;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.EditAdvPositionReq;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

/**
 * @Classname AdvBusinessServiceImpl
 * @Description 业务方法实现
 * @Date 2020/5/28 16:18
 * @Created by 44487
 */
@Service
@Slf4j
public class AdvBusinessServiceImpl implements AdvBusinessService {

    @Autowired
    private AdvPositionService advPositionService;

    @Autowired
    private AdvService advService;

    @Autowired
    private AdvAccessRecordService advAccessRecordService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ApiResp<Long> createAdv(CreateExtendAdvReq req) {

        AdvPositionInfo advPositionInfo = getAdvPositionInfoByCode(req.getApCode());

        if (advPositionInfo == null) {
            return RestfulApiRespFactory.error(MessageFormat.format("此广告位编号：{0}，不存在对应广告位信息，请重新检查", req.getApCode()));
        }

        if (!advPositionInfo.getEnabled()) {
            return RestfulApiRespFactory.error(MessageFormat.format("此广告位编号：{0}，对应广告位不可使用，请重新选择广告位信息", req.getApCode()));
        }

        // 创建
        CreateAdvReq createAdvReq = new CreateAdvReq();
        BeanUtils.copyProperties(req, createAdvReq);
        createAdvReq.setApId(advPositionInfo.getId());

        ApiResp<Long> apiResp = advService.create(createAdvReq);

        if (apiResp.isSuccess()) {

            //添加成功，添加广告位对应广告信息数量
            EditAdvPositionReq editAdvPositionReq = new EditAdvPositionReq();
            editAdvPositionReq.setId(advPositionInfo.getId())
                    .setNum(advPositionInfo.getNum() == null ? 1 : advPositionInfo.getNum() + 1);

            //修改广告位
            advPositionService.edit(editAdvPositionReq);
        }

        return apiResp;
    }


    @Override
    public ApiResp<Void> deleteAdv(DeleteAdvReq deleteAdvReq) {

        //按照单个广告删除
        AdvInfo advInfo = advService.findById(deleteAdvReq.getId());

        if (advInfo == null) {
            return RestfulApiRespFactory.error("要删除的广告对象不存在");
        }

        ApiResp<Void> apiResp = advService.delete(deleteAdvReq);

        if (!apiResp.isSuccess()) {
            return RestfulApiRespFactory.error("广告删除失败");
        }

        //广告位数据信息变更编辑
        AdvPositionInfo advPositionInfo = advPositionService.findById(advInfo.getApId());
        EditAdvPositionReq editAdvPositionReq = new EditAdvPositionReq();
        editAdvPositionReq.setId(advPositionInfo.getId())
                .setNum(advPositionInfo.getNum() - 1);

        ApiResp<Void> advPositionApi = advPositionService.edit(editAdvPositionReq);

        return advPositionApi;

    }

    @Override
    public ApiResp<Pagination<AdvInfo>> queryAdv(QueryAdvExtendReq queryAdvExtendReq) {

        QueryAdvReq baseQueryAdvReq = new QueryAdvReq();
        BeanUtils.copyProperties(queryAdvExtendReq, baseQueryAdvReq);

        baseQueryAdvReq.setMinEndDate(queryAdvExtendReq.getStartDate())
                .setMaxEndDate(queryAdvExtendReq.getEndDate());

        if (queryAdvExtendReq.getApCode() != null) {
            AdvPositionInfo advPositionInfo = getAdvPositionInfoByCode(queryAdvExtendReq.getApCode());
            if (advPositionInfo != null) {
                baseQueryAdvReq.setApId(advPositionInfo.getId());
            }
        }

        return RestfulApiRespFactory.ok(advService.query(baseQueryAdvReq));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ApiResp<Void> browseAdvInfo(BrowseAdvReq browseAdvReq) {

        //广告信息
        AdvInfo advInfo = advService.findById(browseAdvReq.getAdvId());

        if (advInfo == null) {
            return RestfulApiRespFactory.error("该广告对象不存在，请求检查对应广告信息");
        }

        //广告位置信息
        AdvPositionInfo advPositionInfo = advPositionService.findById(advInfo.getApId());

        CreateAdvAccessRecordReq createAdvAccessRecordReq = new CreateAdvAccessRecordReq();
        BeanUtils.copyProperties(browseAdvReq, createAdvAccessRecordReq);

        //创建广告浏览记录信息
        ApiResp<Long> advAccesssApi = advAccessRecordService.create(createAdvAccessRecordReq);
        if (!advAccesssApi.isSuccess()) {
            return RestfulApiRespFactory.error(advAccesssApi.getErrorMessage());
        }

        //编辑广告信息
        EditAdvReq editAdvReq = new EditAdvReq();
        editAdvReq.setId(advInfo.getId());

        //编辑广告位置信息
        EditAdvPositionReq editAdvPositionReq = new EditAdvPositionReq();
        editAdvPositionReq.setId(advPositionInfo.getId());

        if (AdvAccessType.CLICK.equals(browseAdvReq.getAccessType())) {
            //点击数量
            editAdvReq.setClickNum(advInfo.getClickNum() == null ? 1 : advInfo.getClickNum() + 1);
            editAdvPositionReq.setClickNum(advPositionInfo.getClickNum() == null ? 1 : advPositionInfo.getClickNum() + 1);

        } else {
            //曝光数量
            editAdvReq.setExposureNum(advInfo.getExposureNum() == null ? 1 : advInfo.getExposureNum() + 1);
        }

        //修改信息
        ApiResp<Void> advEditApi = advService.edit(editAdvReq);
        if (!advEditApi.isSuccess()) {
            return RestfulApiRespFactory.error("广告浏览记录更新失败");
        }

        ApiResp<Void> advPositionEditApi = advPositionService.edit(editAdvPositionReq);
        if (!advPositionEditApi.isSuccess()) {
            return RestfulApiRespFactory.error("广告位点击数量记录更新失败");
        }

        return RestfulApiRespFactory.ok();
    }


    /**
     * 通过广告位的code去找到对应的广告位信息
     *
     * @param code
     * @return
     */
    private AdvPositionInfo getAdvPositionInfoByCode(String code) {
        QueryAdvPositionReq queryAdvPositionReq = new QueryAdvPositionReq();
        queryAdvPositionReq.setCode(code);
        return advPositionService.query(queryAdvPositionReq).getFirst();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ApiResp<Void> setAdvTop(SetAdvTopReq setAdvTopReq) {
        QueryAdvReq queryAdvReq = new QueryAdvReq();
        queryAdvReq.setApId(setAdvTopReq.getApId());
        queryAdvReq.setOrderBy(new String[]{"orderIndex"});
        queryAdvReq.setOrderType(new String[]{"ASC"});

        List<AdvInfo> advInfoList = advService.query(queryAdvReq).getRecords();
        if (advInfoList != null && !advInfoList.isEmpty()) {
            for (AdvInfo advInfo : advInfoList) {

                EditAdvReq editAdvReq = new EditAdvReq();
                editAdvReq.setId(advInfo.getId());
                if(setAdvTopReq.getId().equals(advInfo.getId())){
                    editAdvReq.setOrderIndex(1);
                }else{
                    editAdvReq.setOrderIndex( advInfo.getOrderIndex() + 1 );
                }

                ApiResp<Void> editApiResp = advService.edit(editAdvReq);
                AssertThrow.assertResp(editApiResp);
            }
        }
        return RestfulApiRespFactory.ok();
    }

}
