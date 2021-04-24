package com.oak.cms.business.adv.req;

import com.oak.api.model.ApiBaseQueryReq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Date： 2020/7/15 19:52
 *
 * @author ZHM
 */

@Schema(description = "查询广告信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class SetAdvTopReq  extends ApiBaseReq {

    @Schema(description = "广告ID")
    private Long id;

    @Schema(description = "广告位ID")
    private Long apId;

}
