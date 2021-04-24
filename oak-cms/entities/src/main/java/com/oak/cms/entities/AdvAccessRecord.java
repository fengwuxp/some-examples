package com.oak.cms.entities;

import com.oak.cms.enums.AdvAccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


@Schema(description = "广告访问记录")
@Entity
@Table(name = "t_adv_access_record", indexes = {
        @Index(name = "index_adv_access_record_adv_id", columnList = "adv_id")
})
@Data
@EqualsAndHashCode(of = {"id"})
public class AdvAccessRecord implements java.io.Serializable {

    private static final long serialVersionUID = 161409941007406125L;
    @Schema(description = "id")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "广告id")
    @Column(name = "adv_id", nullable = false)
    private Long advId;

    @Schema(description = "广告访问类型")
    @Column(name = "access_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdvAccessType accessType;

    @Schema(description = "曝光或点击日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description = "访问客户端的ip（不同的ip才算是有效点击或者是曝光）")
    @Column(name = "ip", nullable = false ,length = 20)
    private String ip;


}
