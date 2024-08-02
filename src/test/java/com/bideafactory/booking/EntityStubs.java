package com.bideafactory.booking;

import com.bideafactory.booking.config.JacksonConfig;
import com.bideafactory.booking.dto.DtoInBook;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class EntityStubs {

    private static final EntityStubs INSTANCE = new EntityStubs();
    private final ObjectMapper objectMapper;

    private EntityStubs() {
        this.objectMapper = new JacksonConfig().objectMapper();
    }

    public static EntityStubs getInstance() {
        return INSTANCE;
    }

    public DtoInBook getDtoInBook() throws IOException {
        return objectMapper.readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("mock/DtoInBook.json"),
                DtoInBook.class
        );
    }
}
