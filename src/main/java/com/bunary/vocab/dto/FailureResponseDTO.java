package com.bunary.vocab.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class FailureResponseDTO {
    private LocalDateTime timestamp;
    private int statusCode;
    private List<String> error;
    private Map<String, String> fieldError;

    public FailureResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

}