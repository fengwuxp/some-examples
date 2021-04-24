package com.oak.api;


/**
 * 系统设置值持有者
 * 用于获取系统相关配置
 * <p>
 * 1：数据库配置表中的配置 {@link com.oak.api.entities.system.Setting}
 * 2: spring {@link org.springframework.core.env.Environment} 中的变量
 * </p>
 *
 * @author wxup
 */
public interface SystemSettingValueHolder {

    /**
     * 获取配置
     *
     * @param key          配置key
     * @param defaultValue 配置的默认值
     * @return 配置的值
     */
    String getValue(String key, String defaultValue);

    /**
     * 获取配置，尝试将配置值转换为预期类型 {@param targetType}
     *
     * @param key          配置key
     * @param targetType   配置值的类型
     * @param defaultValue 配置的默认值
     * @param <T>          类型变量
     * @return 配置的值
     */
    <T> T getValue(String key, Class<T> targetType, T defaultValue);


    /**
     * 明确从数据库配置表 {@link com.oak.api.entities.system.Setting}中获取数据
     * 将尝试按照 {@link com.oak.api.entities.system.Setting#getType}的类型进行值转换
     *
     * @param key          配置key
     * @param defaultValue 配置值的类型
     * @param <T>          类型变量
     * @return 配置的值
     */
    <T> T getSettingValue(String key, T defaultValue);
}
