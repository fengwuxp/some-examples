package com.oak.app.services.view.info;

import com.levin.commons.service.domain.Desc;
import com.oak.app.enums.ViewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ChenXiaoBin
 * on 2020-05-21
 */
@Schema(description = "客户端视图")
@Data
public class AppViewInfo implements Serializable {
    @Desc("ID")
    private Long id;
    @Desc("视图名称")
    private String name;
    @Desc("视图编码")
    private String code;
    @Desc("视图类型")
    private ViewType type;
    @Desc("访问路径")
    private String path;
    @Desc("参数模板")
    private String params;


    public String getParams(Object... objects) {
        if (this.params != null && objects != null) {
            String result = this.params;
            int i = 0;
            Object[] var4 = objects;
            int var5 = objects.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Object obj = var4[var6];
                result = result.replace("{" + i++ + "}", obj != null ? obj.toString() : "");
            }

            return result;
        } else {
            return null;
        }
    }
}
