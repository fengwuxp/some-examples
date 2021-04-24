package com.oak.api.entities.system;

import com.oak.api.enums.ClientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wxup
 */
@Entity
@Schema(description = "客户端渠道信息")
@Table(name = "t_client_channel",
        indexes = {
                @Index(name = "index_client_channel_code", columnList = "code"),
                @Index(name = "index_client_channel_name", columnList = "name")
        })
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"code"})
public class ClientChannel implements java.io.Serializable {

    private static final long serialVersionUID = 7386303891356876736L;

    @Schema(description = "编号")
    @Id
    @Column(name = "code", length = 32)
    private String code;

    @Schema(description = "名称")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Schema(description = "客户端类型")
    @Column(name = "client_type", length = 16)
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Schema(description = "排序")
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Schema(description = "启用")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Schema(description = "创建日期")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(name = "updateTime", description = "更新日期")
    @Column(name = "update_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public ClientChannel(String code) {
        this.code = code;
    }
}
