package com.oak.basic.uuid;

import com.wuxp.basic.uuid.sn.AbstractSnGenerateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的订单生成策略
 * 按照 yyyyMMddHHmm+4位的自增长订单号
 * <p>
 * 使用redis来作为sn的自增长，每一分钟最多支持生成1w个订单号
 *
 * @author wxup
 */
@Slf4j
public class RedissonSnGenerateStrategy extends AbstractSnGenerateStrategy implements InitializingBean, BeanFactoryAware {

    private static final String REDIS_ORDER_SN_KEY = "REDIS_ORDER_SN_";

    private BeanFactory beanFactory;

    /**
     * 上一次的key,在同一分钟内相同，如果不同则需要给对应key的 RAtomicLong 加上过期时间
     */
    private String preKey;

    private RedissonClient redissonClient;


    @Override
    protected String nextId(String orderPrefix, SnType type) {
        String key = REDIS_ORDER_SN_KEY + orderPrefix + type.getCode();
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        if (!orderPrefix.equals(preKey)) {
            // TODO 调整为分布式锁
            // redissonClient.getLock().tryLock()
            synchronized (this) {
                if (!key.equals(preKey)) {
                    if (atomicLong.expire(1, TimeUnit.MINUTES)) {
                        preKey = key;
                    }
                }
            }
        }
        return String.valueOf(atomicLong.incrementAndGet());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.redissonClient == null) {
            this.redissonClient = this.beanFactory.getBean(RedissonClient.class);
        }
    }
}
