package com.upgrad.bookingservice.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String string = p.getText().trim();
        if (string.length() == 0) {
            return null;
        }
        if (string.contains("T")) {
            return LocalDateTime.parse(string, DATE_TIME_FORMATTER);
        } else {
            return LocalDateTime.parse(string + "T00:00:00", DATE_TIME_FORMATTER);
        }
    }
}
