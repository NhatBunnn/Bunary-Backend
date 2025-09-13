package com.bunary.vocab.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class SuccessReponseDTO<T> {
    private LocalDateTime timestamp;
    private int statusCode;
    private List<String> message;
    private T data;
    private Map<String, Object> dataMap;
    private PageResponseDTO pagination;

    public SuccessReponseDTO(LocalDateTime timestamp, int statusCode, List<String> message, T data) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public SuccessReponseDTO(LocalDateTime timestamp, int statusCode, List<String> message, T data,
            Map<String, Object> dataMap) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dataMap = dataMap;
    }

    public SuccessReponseDTO(LocalDateTime timestamp, int statusCode, List<String> message, T data,
            Map<String, Object> dataMap, PageResponseDTO pagination) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dataMap = dataMap;
        this.pagination = pagination;
    }

    public SuccessReponseDTO(LocalDateTime timestamp, int statusCode, List<String> message, T data,
            PageResponseDTO pagination) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }

    public SuccessReponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

}
