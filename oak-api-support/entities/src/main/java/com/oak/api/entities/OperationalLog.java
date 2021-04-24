package com.oak.api.entities;

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
 * @author wxup
 */
@Entity
@Schema(description = "操作日志")
@Table(
        name = "t_operational_log",
        indexes = {
                @Index(name = "index_operational_log_operational_id", columnList = "operational_id"),
                @Index(name = "index_operational_log_operational_name", columnList = "operational_name"),
                @Index(name = "index_operational_log_url", columnList = "url"),
                @Index(name = "u_index_operational_log_type_resource_id", columnList = "type"),
                @Index(name = "u_index_operational_log_type_resource_id", columnList = "target_resource_id")
        }
)
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class OperationalLog implements Serializable {


    private static final long serialVersionUID = -3309234304685373187L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "操作类型")
    @Column(name = "type", nullable = false, length = 32)
    private String type;

    @Schema(description = "操作动做")
    @Column(name = "action", nullable = false, length = 32)
    private String action;

    @Schema(description = "操作的目标资源id")
    @Column(name = "target_resource_id", length = 32)
    private String targetResourceId;

    @Schema(description = "操作内容")
    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @Schema(description = "请求参数")
    @Column(name = "req")
    @Lob
    private String req;

    @Schema(description = "响应")
    @Column(name = "resp")
    @Lob
    private String resp;

    @Schema(description = "创建时间")
    @Column(name = "create_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "操作人id")
    @Column(name = "operational_id", nullable = false)
    private Long operationalId;

    @Schema(description = "操作人名称")
    @Column(name = "operational_name", nullable = false, length = 32)
    private String operationalName;

    @Schema(description = "IP")
    @Column(name = "ip", nullable = false, length = 64)
    private String ip;

    @Schema(description = "Url")
    @Column(name = "url")
    private String url;

    @Schema(description = "来源编码")
    @Column(name = "source_code", length = 32)
    private String sourceCode;

}
