package com.oak.app.entities;


import com.oak.app.enums.ViewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Schema(description = "客户端视图")
@Table(name = "t_app_view")
@Data
@EqualsAndHashCode(of = {"id"})
public class AppView implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "视图名称")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "视图编码")
    @Column(name = "code", nullable = false)
    private String code;

    @Schema(description = "视图类型")
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ViewType type;

    @Schema(description = "访问路径")
    @Column(name = "path", nullable = false)
    private String path;

    @Schema(description = "参数模板")
    @Column(name = "params", length = 2000)
    private String params;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

}
