package com.zagvladimir.util;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@Component
public class UUIDGenerator {
    private final Supplier<UUID> uuidSupplier = UUID::randomUUID;

    public String getUuid() {
        return uuidSupplier.get().toString();
    }
}
