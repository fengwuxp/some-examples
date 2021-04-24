package com.oak.api.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.NamedCacheResolver;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于支持分页cache缓存数据
 *
 * @author wxup
 */
@Slf4j
public class PaginationCacheResolver extends NamedCacheResolver {


    // 最多统一缓存前3页的数据
//    private static short MAX_CACHE_PAGE_NUM = 3;

//    private Map<Class<? extends PaginationCacheSupportReq>, Short> maxPagesMap = new ConcurrentHashMap<>(16);


    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        PaginationCacheSupportReq cacheSupportReq = Arrays.stream(context.getArgs())
                .filter(arg -> arg instanceof PaginationCacheSupportReq)
                .map(req -> (PaginationCacheSupportReq) req)
                .findFirst()
                .orElse(null);
        if (cacheSupportReq == null) {
            return Collections.emptyList();
        }
        String[] cacheIdentities = cacheSupportReq.getCacheIdentities();
        if (cacheIdentities == null || cacheIdentities.length == 0) {
            return Collections.emptyList();
        }
        Class<?> aClass = context.getTarget().getClass();
        List<String> cacheNames = Arrays.stream(cacheIdentities)
                .filter(StringUtils::hasText)
                .map((identity) -> MessageFormat.format("pagination_{0}_{1}", aClass.getName(), identity))
                .collect(Collectors.toList());

        if (log.isDebugEnabled()) {
            log.debug("解析到的分页缓存name {}", cacheNames);
        }
        return cacheNames;
    }

//    public void putCacheMaxPages(Class<? extends PaginationCacheSupportReq> clazz, Short maxNum) {
//        maxPagesMap.put(clazz, maxNum);
//    }

}
