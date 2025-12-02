package com.bunary.vocab.dto;

import java.time.LocalDateTime;

import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class SuccessReponseDTO<T> {
    private LocalDateTime timestamp;
    private int statusCode;
    private String message;
    private T data;
    private PageResponseDTO pagination;

    public SuccessReponseDTO(LocalDateTime timestamp, int statusCode, String message, T data,
            PageResponseDTO pagination) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }

}
