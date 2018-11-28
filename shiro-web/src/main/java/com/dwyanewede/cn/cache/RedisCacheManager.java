package com.dwyanewede.cn.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManager implements CacheManager {

    private RedisCache redisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return redisCache;
    }

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }
}