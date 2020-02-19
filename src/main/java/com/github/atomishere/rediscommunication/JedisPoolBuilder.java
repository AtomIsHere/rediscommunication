package com.github.atomishere.rediscommunication;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

public final class JedisPoolBuilder {
    public static JedisPoolBuilder newBuilder() {
        return new JedisPoolBuilder();
    }

    private JedisPoolBuilder() {
    }

    private String redisHost = Protocol.DEFAULT_HOST;
    private Integer redisPort = Protocol.DEFAULT_PORT;
    private Integer redisTimeout = Protocol.DEFAULT_TIMEOUT;
    private String redisPassword = null;

    public JedisPoolBuilder setRedisHost(String redisHost) {
        this.redisHost = redisHost;
        return this;
    }

    public JedisPoolBuilder setRedisPort(Integer redisPort) {
        this.redisPort = redisPort;
        return this;
    }

    public JedisPoolBuilder setRedisTimeout(Integer redisTimeout) {
        this.redisTimeout = redisTimeout;
        return this;
    }

    public JedisPoolBuilder setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
        return this;
    }

    public JedisPool build() {
        if(redisPassword == null) {
            return new JedisPool(PoolConfigUtils.buildPoolConfig(), redisHost, redisPort, redisTimeout);
        } else {
            return new JedisPool(PoolConfigUtils.buildPoolConfig(), redisHost, redisPort, redisTimeout, redisPassword);
        }
    }
}
