package com.bunary.vocab.helpers;

import java.util.Map;
import java.util.Set;

public class FilterFieldConfig {

    // map entityName -> set of allowed filter fields
    private static final Map<String, Set<String>> ALLOWED_FIELDS = Map.of(
            "user", Set.of("fullName", "email", "role", "age", "createdAt"),
            "wordset", Set.of("name", "visibility", "createdAt"),
            "collection", Set.of("title", "ownerId", "createdAt"));

    public static Set<String> getAllowedFields(String entityName) {
        return ALLOWED_FIELDS.getOrDefault(entityName.toLowerCase(), Set.of());
    }
}
