package com.bunary.vocab.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class FailureResponseDTO {
    private LocalDateTime timestamp;
    private int statusCode;
    private String errorCode;
    private String message;

    public FailureResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

}