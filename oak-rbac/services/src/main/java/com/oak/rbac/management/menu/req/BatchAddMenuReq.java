package com.oak.rbac.management.menu.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Classname BatchAddMenuReq
 * @Description 批量添加菜单查询
 * @Date 2020/6/22 16:33
 * @Created by 44487
 * @Author ZHM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "批量添加菜单查询")
public class BatchAddMenuReq extends ApiBaseReq {

    private AddMenuInfoReq[] addMenuInfoReqs;

}
