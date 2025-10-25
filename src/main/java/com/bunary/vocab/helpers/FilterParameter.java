package com.bunary.vocab.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterParameter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Lấy keyword
    public static String filterKeyword(Map<String, String> parameters) {
        String keyword = parameters.get("keyword");
        return keyword != null ? keyword.trim() : null; // Loại bỏ khoảng trắng thừa
    }

    // Filter đơn giản (equals) dựa vào whitelist entity
    public static Map<String, String> filterSimple(Map<String, String> parameters, String entityName) {
        Set<String> allowed = FilterFieldConfig.getAllowedFields(entityName);
        return parameters.entrySet().stream()
                .filter(entry -> allowed.contains(entry.getKey()) && !entry.getKey().contains("["))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // Filter operator (gt, lt, gte, lte, between)
    public static Map<String, Map<String, String>> filterOperators(Map<String, String> parameters, String entityName) {
        Set<String> allowed = FilterFieldConfig.getAllowedFields(entityName);
        return parameters.entrySet().stream()
                .filter(entry -> entry.getKey().contains("[") && allowed.contains(entry.getKey().split("\\[")[0]))
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().split("\\[")[0], // field
                        Collectors.toMap(
                                entry -> entry.getKey().replaceAll(".*\\[|\\]", ""), // operator
                                Map.Entry::getValue)));
    }

    // Parse LocalDate
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
}