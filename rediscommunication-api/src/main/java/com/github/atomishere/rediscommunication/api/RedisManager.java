package com.github.atomishere.rediscommunication.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@RequiredArgsConstructor
public class RedisManager {
    @Getter
    private static RedisManager instance;

    public static void setInstance(RedisManager instance) {
        if(RedisManager.instance != null) {
            return;
        }

        RedisManager.instance = instance;
    }

    private final Set<JedisPubSub> listeners = new CopyOnWriteArraySet<>();

    @Getter
    private final JedisPool jedisPool;
    @Getter
    private final ObjectMapper mapper;

    public void registerListener(JedisPubSub listener, String channel) {
        try(Jedis jedis = jedisPool.getResource()) {
            Thread listenerThread = new Thread(() -> jedis.subscribe(listener, channel), "RedisCommunication - " + channel);
            listenerThread.start();
            listeners.add(listener);
        }
    }

    public void unregisterListener(JedisPubSub listener) {
        listener.unsubscribe();
        listeners.remove(listener);
    }

    public void unregisterAll() {
        listeners.forEach(this::unregisterListener);
        listeners.clear();
    }

    public void sendPojo(Object pojo, String channel, String serverName) {
        String json;
        try {
            json = mapper.writeValueAsString(pojo);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return;
        }

        try(Jedis jedis = jedisPool.getResource()) {
            jedis.publish(channel, serverName + ":" + json);
        }
    }

    public void shutdown() {
        unregisterAll();
        jedisPool.destroy();
        RedisManager.instance = null;
    }
}
