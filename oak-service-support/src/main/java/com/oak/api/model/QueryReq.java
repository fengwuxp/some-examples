package com.oak.api.model;

import com.wuxp.api.model.QueryType;

public interface QueryReq {


    Integer getQueryPage();

    Integer getQuerySize();

    QueryType getQueryType();

    String[] getOrderBy();

    String[] getOrderType();

    /**
     * 是否有排序
     *
     * @return
     */
    default boolean isOrderBy() {
        return this.getOrderBy() != null && this.getOrderBy().length > 0 && this.getOrderType() != null && this.getOrderType().length > 0 && this.getOrderBy().length == this.getOrderType().length && this.getOrderBy()[0] != null && !this.getOrderBy()[0].isEmpty() && this.getOrderType()[0] != null && !this.getOrderType()[0].isEmpty();
    }


}
