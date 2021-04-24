package com.oak.messages.services.notice.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.messages.entities.E_NoticeMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询通知消息
 * 2020-7-7 13:59:04
 */
@Schema(description = "查询通知消息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryNoticeMessageReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "分组")
    private String group;

    @Schema(description = "消息分类")
    private String businessType;

    @Schema(description = "消息模板CODE")
    private String code;

    @Schema(description = "标题")
    @Contains(E_NoticeMessage.title)
    private String likeTitle;

    @Schema(description = "消息关联视图编辑码")
    private String viewCode;


    @Schema(description = "是否已读")
    private Boolean read;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryNoticeMessageReq() {
    }

    public QueryNoticeMessageReq(Long id) {
        this.id = id;
    }
}
