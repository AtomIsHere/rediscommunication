package com.github.atomishere.rediscommunication.spigot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.atomishere.rediscommunication.JedisPoolBuilder;
import com.github.atomishere.rediscommunication.api.RedisManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Protocol;


public class RedisCommunicationSpigot extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        FileConfiguration config = getConfig();

        RedisManager.setInstance(new RedisManager(JedisPoolBuilder.newBuilder()
                .setRedisHost(config.getString("redis_host", Protocol.DEFAULT_HOST))
                .setRedisPort(config.getInt("redis_port", Protocol.DEFAULT_PORT))
                .setRedisTimeout(config.getInt("redis_timeout", Protocol.DEFAULT_TIMEOUT))
                .setRedisPassword(config.getString("redis_password", null)).build(), new ObjectMapper()));
    }

    @Override
    public void onDisable() {
        RedisManager.getInstance().shutdown();
    }

}
