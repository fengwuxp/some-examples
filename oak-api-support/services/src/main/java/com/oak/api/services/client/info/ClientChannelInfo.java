package com.oak.api.services.client.info;

import com.oak.api.enums.ClientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * ClientChannel
 * 2020-3-11 11:05:46
 */
@Schema(description = "ClientChannel")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"code"})
@ToString(exclude = {})
public class ClientChannelInfo implements Serializable {

    @Schema(description = "编号")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "客户端类型")
    private ClientType clientType;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "启用")
    private Boolean enabled;

    @Schema(description = "创建日期")
    private Date createTime;

    @Schema(description = "更新日期")
    private Date updateTime;


}
