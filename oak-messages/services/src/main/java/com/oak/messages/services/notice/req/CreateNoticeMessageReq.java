package com.oak.messages.services.notice.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.messages.entities.NoticeMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 创建NoticeMessage
 * 2020-7-7 13:59:04
 */
@Schema(description = "创建CreateNoticeMessageReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateNoticeMessageReq extends ApiBaseReq {

    @Schema(description = "用户id")
    @NotNull
    private Long memberId;

    @Schema(description = "消息模板CODE")
    @NotNull
    @Size(max = 32)
    private String code;

    @Schema(description = "消息分组")
    @NotNull
    private String group = NoticeMessage.DEFAULT_GROUP;

    @Schema(description = "消息业务分类")
    @NotNull
    @Size(max = 32)
    private String businessType = NoticeMessage.DEFAULT_MESSAGE;

    @Schema(description = "标题")
    @NotNull
    @Size(max = 128)
    private String title;

    @Schema(description = "消息内容")
    @NotNull
    @Size(max = 512)
    private String content;

    @Schema(description = "消息关联视图编辑码")
    @Size(max = 32)
    private String viewCode;

    @Schema(description = "消息关联视图参数，以查询字符串的方式存储")
    private String viewParams;


}
