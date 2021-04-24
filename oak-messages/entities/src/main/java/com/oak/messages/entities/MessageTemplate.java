package com.oak.messages.entities;


import com.oak.messages.enums.MessageShowType;
import com.oak.messages.enums.SimpleMessagePushType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author wuxp
 */
@Schema(description = "消息模板")
@Entity
@Table(name = "t_message_template", indexes = {
        @Index(name = "index_message_template_code", columnList = "code")
})
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class MessageTemplate implements Serializable {


    private static final long serialVersionUID = -6848451781133910141L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "模板名称")
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Schema(description = "推送类型")
    @Column(name = "push_type", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private SimpleMessagePushType pushType;

    @Schema(description = "消息展示类型")
    @Column(name = "show_type", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private MessageShowType showType;

    @Schema(description = "消息的业务分类")
    @Column(name = "business_type", nullable = false, length = 32)
    private String businessType;

    @Schema(description = "消息标题")
    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Schema(description = "编号")
    @Column(name = "code", nullable = false, length = 32)
    private String code;

    @Schema(description = "内容")
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Schema(description = "跳转视图编码")
    @Column(name = "view_code")
    private String viewCode;

    @Schema(description = "跳转视图URL")
    @Column(name = "view_url")
    private String viewUrl;

    @Schema(description = "扩展数据")
    @Column(name = "ext_data")
    private String extData;

    @Schema(description = "是否启用")
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Schema(description = "分组")
    @Column(name = "`group`", nullable = false)
    private String group;

    @Schema(description = "是否可修改的")
    @Column(name = "modifiable", nullable = false)
    private Boolean modifiable = true;

    @Schema(description = "外部模板ID")
    @Column(name = "out_template_id")
    private String outTemplateId;

    @Schema(description = "排序")
    @Column(name = "order_code", nullable = false)
    private Integer orderCode = 0;

    @Schema(description = "推送后的信息是否需要持久化")
    @Column(name = "is_persistence", nullable = false)
    private Boolean persistence;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

}
