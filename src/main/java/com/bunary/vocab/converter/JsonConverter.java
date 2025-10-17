package com.bunary.vocab.converter;

import jakarta.persistence.AttributeConverter;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
