package com.github.atomishere.rediscommunication.test.bungee;

import com.github.atomishere.rediscommunication.api.RedisManager;
import com.github.atomishere.rediscommunication.test.PojoListener;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeTest extends Plugin {
    @Override
    public void onEnable() {
        RedisManager.getInstance().registerListener(new CommandListener(getLogger()), "test_command");
        RedisManager.getInstance().registerListener(new PojoListener(RedisManager.getInstance().getMapper(), getLogger()), "simple_pojo");
    }
}
