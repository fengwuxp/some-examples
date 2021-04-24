package com.oak.messages.services.template.info;

import com.oak.messages.enums.MessageShowType;
import com.oak.messages.enums.SimpleMessagePushType;
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
 * 消息模板
 * 2020-7-7 13:58:37
 */
@Schema(description = "消息模板")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MessageTemplateInfo implements Serializable {

    private static final long serialVersionUID = -5329193760284761092L;
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "消息类型")
    private SimpleMessagePushType pushType;

    @Schema(description = "消息展示类型")
    private MessageShowType showType;

    @Schema(description = "消息的业务分类")
    private String businessType;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "跳转视图编码")
    private String viewCode;

    @Schema(description = "跳转视图URL")
    private String viewUrl;

    @Schema(description = "扩展数据")
    private String extData;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "分组")
    private String group;

    @Schema(description = "是否可修改的")
    private Boolean modifiable;

    @Schema(description = "推送后的信息是否需要持久化")
    private Boolean persistence;

    @Schema(description = "外部模板ID")
    private String outTemplateId;

    @Schema(description = "排序")
    private Integer orderCode;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;


}
