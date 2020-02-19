package com.github.atomishere.rediscommunication.test;

import lombok.Data;

import java.util.UUID;

@Data
public class SimplePojo {
    private final String testString;
    private final int testInt;
    private final UUID testUuid;
}
