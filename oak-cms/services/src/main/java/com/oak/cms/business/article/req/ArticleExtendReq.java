package com.oak.cms.business.article.req;

import com.levin.commons.dao.annotation.Contains;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.cms.enums.ArticleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Classname ArticleExtendReq
 * @Description 文章请求对象
 * @Date 2020/5/29 15:57
 * @Created by 44487
 */
@Schema(description = "查询广告信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ArticleExtendReq extends ApiBaseQueryReq {

    @Schema(description = "文章ID")
    private Long id;

    @Schema(description = "栏目ID ，特殊说明： (如果 栏目id和栏目编号都写的，优先按照 栏目编号进行搜索)")
    private Long channelId;

    @Schema(description = "栏目编号，特殊说明：如果栏目编号是父级编号（具有下级）则会把对应的下级栏目关联的文章都获取出来 ")
    private String channelCode;

    @Schema(description = "标题")
    @Contains
    private String title;

    @Schema(description = "状态")
    private ArticleStatus status;

    @Schema(description = "开始发布日期")
    private Date startPublishTime;

    @Schema(description = "结束发布日期")
    private Date endPublishTime;

    @Schema(description = "关联来源")
    @Contains
    private String sourceCode;

}
