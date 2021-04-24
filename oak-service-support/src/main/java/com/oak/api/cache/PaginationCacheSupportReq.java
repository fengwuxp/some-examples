package com.oak.api.cache;


import java.beans.Transient;

/**
 * 用于支持 spring cache 分页缓存的 req
 * {@link PaginationCacheResolver}
 *
 * @author wxup
 */
public interface PaginationCacheSupportReq {


    /**
     * 该方法必须是  {@Transient}的
     *
     * @return
     */
    @Transient
    String getCacheIdentity();

    /**
     * 该方法必须是  {@Transient}的
     *
     * @return
     */
    @Transient
    default String[] getCacheIdentities() {
        return new String[]{this.getCacheIdentity()};
    }

}
