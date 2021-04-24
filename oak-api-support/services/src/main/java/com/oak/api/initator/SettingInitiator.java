package com.oak.api.initator;

import com.oak.api.enums.SettingValueType;
import com.oak.api.services.system.SystemService;
import com.oak.api.services.system.info.SettingGroupInfo;
import com.oak.api.services.system.req.CreateSettingGroupReq;
import com.oak.api.services.system.req.GetSettingReq;
import com.oak.api.services.system.req.QuerySettingGroupReq;
import com.oak.api.services.system.req.SaveSettingReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

/**
 * 系统设置初始化器
 *
 * @author wxup
 */
@Slf4j
public class SettingInitiator extends AbstractBaseInitiator<SettingModel> {

    @Autowired
    private SystemService systemService;

    @Override
    protected boolean initSingleItem(SettingModel data) {

        Pagination<SettingGroupInfo> pagination = systemService.querySettingGroup(new QuerySettingGroupReq(data.getGroup()));
        SettingGroupInfo first = pagination.getFirst();
        if (first == null) {
            //创建分组
            CreateSettingGroupReq groupEvt = new CreateSettingGroupReq();
            groupEvt.setShow(data.getShow());
            groupEvt.setName(data.getGroup());
            groupEvt.setOrderIndex(data.getIndex());
            String settingGroup = systemService.createSettingGroup(groupEvt);
            if (log.isDebugEnabled()) {
                log.debug(MessageFormat.format("创建设置分组【{0}】->{1}", data.getGroup(), settingGroup));
            }
        }

        int j = 0;
        for (SaveSettingReq evt : data.getSettings()) {
            String config = systemService.getSetting(new GetSettingReq(evt.getName(), false));
            if (config != null) {
                continue;
            }
            evt.setGroupName(data.getGroup());
            if (evt.getShow() == null) {
                evt.setShow(true);
            }
            if (evt.getType() == null) {
                evt.setType(SettingValueType.TEXT);
            }
            if (evt.getOrderIndex() == null) {
                evt.setOrderIndex(j);
            }

            ApiResp<Void> resp = systemService.saveSetting(evt);
            if (log.isDebugEnabled()) {
                log.debug("创建设置【" + evt + "】->" + resp);
            }
            j++;
        }
        return j != 0;
    }
}
