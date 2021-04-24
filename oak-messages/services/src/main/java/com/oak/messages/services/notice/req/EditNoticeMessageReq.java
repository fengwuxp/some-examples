package com.oak.messages.services.notice.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 编辑通知消息
 * 2020-7-7 13:59:04
 */
@Schema(description = "编辑通知消息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditNoticeMessageReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "用户id")
    @NotNull
    @Eq(require = true)
    private Long memberId;

    @Size(max = 128)
    @Schema(description = "标题")
    @Update
    private String title;

    @Size(max = 512)
    @Schema(description = "消息内容")
    @Update
    private String content;

    @Schema(description = "消息关联视图参数，以查询字符串的方式存储")
    @Update
    private String viewParams;

    @Schema(description = "是否已读")
    @Update
    private Boolean read;


    public EditNoticeMessageReq() {
    }

    public EditNoticeMessageReq(Long id) {
        this.id = id;
    }
}
