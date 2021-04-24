package com.oak.api.services.client.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除ClientChannel
 *  2020-3-11 11:05:46
 */
@Schema(description = "删除ClientChannel")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteClientChannelReq extends ApiBaseReq {

    @Schema(description = "编号")
    private String code;

    @Schema(description = "编号集合")
    @In("code")
    private String[] codes;

    public DeleteClientChannelReq() {
    }

    public DeleteClientChannelReq(String code) {
        this.code = code;
    }

}
