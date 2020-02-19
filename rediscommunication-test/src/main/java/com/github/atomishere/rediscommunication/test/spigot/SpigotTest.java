package com.github.atomishere.rediscommunication.test.spigot;

import com.github.atomishere.rediscommunication.api.RedisManager;
import com.github.atomishere.rediscommunication.test.SimplePojo;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SpigotTest extends JavaPlugin {
    @Override
    public void onEnable() {
        RedisManager.getInstance().sendCommand("test_command", new String[]{"test1", "test2", "test3"}, "testServer");
        RedisManager.getInstance().sendPojo(new SimplePojo("test", 123, UUID.randomUUID()), "simple_pojo", "testServer");
    }
}
