package com.xuncai.parking.config.redis;

import com.xuncai.parking.config.Exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
