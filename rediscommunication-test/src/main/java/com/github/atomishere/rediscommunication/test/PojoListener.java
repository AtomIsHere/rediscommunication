package com.github.atomishere.rediscommunication.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.atomishere.rediscommunication.api.listeners.RedisPojoListener;

import java.util.logging.Logger;

public class PojoListener extends RedisPojoListener<SimplePojo> {
    private final Logger log;

    public PojoListener(ObjectMapper mapper, Logger log) {
        super(mapper, SimplePojo.class);

        this.log = log;
    }

    @Override
    protected void onCommand(String serverName, SimplePojo pojo) {
        log.info("Pojo received from: " + serverName +
                "\n    String: " + pojo.getTestString() +
                "\n  Int: " + pojo.getTestInt() +
                "\n    UUID: " + pojo.getTestUuid());
    }
}
