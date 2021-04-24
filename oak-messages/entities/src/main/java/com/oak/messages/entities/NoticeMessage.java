package com.oak.messages.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author wuxp
 */
@Entity
@Schema(description = "通知消息")
@Table(name = "t_notice_message", indexes = {
        @Index(name = "index_notice_message_member_id_group", columnList = "member_id,group"),
})
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class NoticeMessage implements Serializable {


    public static final String DEFAULT_MESSAGE = "SYSTEM";
    public static final String DEFAULT_GROUP = "DEFAULT";

    private static final long serialVersionUID = -312092397367218257L;
    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "用户id")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "消息分组")
    @Column(name = "`group`", length = 32, nullable = false)
    private String group = DEFAULT_GROUP;

    @Schema(description = "消息的业务分类")
    @Column(name = "business_type", nullable = false, length = 32)
    private String businessType;

    @Schema(description = "消息模板CODE")
    @Column(name = "code", nullable = false, length = 32)
    private String code;

    @Schema(description = "标题")
    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Schema(description = "消息内容")
    @Column(name = "content", nullable = false, length = 512)
    private String content;

    @Schema(description = "消息关联视图编辑码")
    @Column(name = "view_code", length = 32)
    private String viewCode;

    @Schema(description = "消息关联视图参数，以查询字符串的方式存储")
    @Column(name = "view_params")
    private String viewParams;

    @Schema(description = "是否已读")
    @Column(name = "is_read", nullable = false)
    private Boolean read;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

}
