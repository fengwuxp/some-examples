package com.oak.messages.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Schema(description = "短信发送日志")
@Table(name = "t_sm_log",indexes = {
        @Index(name = "index_sms_log_mobile_phone",columnList = "mobile_phone")
})
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SmLog implements java.io.Serializable {

    private static final long serialVersionUID = 903140818030308149L;
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "消息唯一ID")
    @Column(name = "msg_id", length = 100)
    private String msgId;

    @Schema(description = "发送手机号")
    @Column(name = "mobile_phone", nullable = false, length = 20)
    private String mobilePhone;

    @Schema(description = "发送内容（模板短信，记录要替换的变量）")
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Schema(description = "发送结果码")
    @Column(name = "result_code", length = 300)
    private String resultCode;

//    @Schema(description = "发送时间")
//    @Column(name = "send_time", nullable = false, length = 19)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date sendTime;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

}
