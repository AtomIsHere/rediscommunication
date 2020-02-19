package com.github.atomishere.rediscommunication.api.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class RedisPojoListener<T> extends JedisPubSub {
    private final ObjectMapper mapper;

    @Getter
    private final Class<T> pojoClass;

    @Override
    public void onMessage(String channel, String message) {
        int index = message.indexOf(":");
        if(index == -1) {
            return;
        }

        String serverName;
        try {
            serverName = message.substring(0, index - 1);
        } catch(IndexOutOfBoundsException ioobe) {
            return;
        }

        T pojo;
        try {
            pojo = mapper.readValue(message.substring(index + 1), getPojoClass());
        } catch(IOException ioe) {
            return;
        }

        onCommand(serverName, pojo);
    }

    protected abstract void onCommand(String serverName, T pojo);
}
