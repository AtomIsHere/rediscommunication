package com.github.atomishere.rediscommunication.bungee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.atomishere.rediscommunication.JedisPoolBuilder;
import com.github.atomishere.rediscommunication.PoolConfigUtils;
import com.github.atomishere.rediscommunication.api.RedisManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import redis.clients.jedis.Protocol;

import java.io.File;
import java.io.IOException;

public class RedisCommunicationBungee extends Plugin {
    @Override
    public void onEnable() {
        Configuration config;

        File configFile = new File(getDataFolder(), "config.yml");
        if(configFile.exists()) {
            try {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                getLogger().severe("Could not load config... using defaults.");
                config = new Configuration();
            }
        } else {
            getLogger().severe("Could not find config... using defaults.");
            config = new Configuration();
        }


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
