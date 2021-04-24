package com.oak.messages.services.notice.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;


/**
 * 通知消息
 * 2020-7-7 13:59:04
 */
@Schema(description = "通知消息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class NoticeMessageInfo implements Serializable {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "分组")
    private String group;

    @Schema(description = "消息的业务分类")
    private String businessType;

    @Schema(description = "消息模板CODE")
    private String code;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "消息关联视图编辑码")
    private String viewCode;

    @Schema(description = "消息关联视图参数，以查询字符串的方式存储")
    private String viewParams;

    @Schema(description = "是否已读")
    private Boolean read;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;


}
