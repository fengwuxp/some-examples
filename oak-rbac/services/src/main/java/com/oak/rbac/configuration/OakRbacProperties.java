package com.oak.rbac.configuration;

import lombok.Data;

import static com.oak.rbac.entities.OakAdminUser.DEFAULT_BUSINESS_MODULE;

/**
 * @author wxup
 */
@Data
public class OakRbacProperties {

    static final String PREFIX = "oak.rbac";

    /**
     * 业务模块，默认：default
     */
    private String businessModule = DEFAULT_BUSINESS_MODULE;
}
