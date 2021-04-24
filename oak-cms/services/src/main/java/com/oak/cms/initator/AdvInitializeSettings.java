package com.oak.cms.initator;

import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname AdvInitator
 * @Description 广告位以及广告信息初始化
 * @Date 2020/5/26 11:13
 * @Created by 44487
 */
@Schema(description = "广告初始化配置")
@Data
public class AdvInitializeSettings implements Serializable {

    @Schema(description = "广告位信息初始化对象")
    private CreateAdvPositionReq createAdvPositionReq;

    @Schema(description = "广告信息初始化列表对象")
    private List<CreateAdvReq> advReqList;
}
