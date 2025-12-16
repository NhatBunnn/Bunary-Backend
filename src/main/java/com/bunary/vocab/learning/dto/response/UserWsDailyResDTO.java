package com.bunary.vocab.learning.dto.response;

import java.time.LocalDate;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UserWsDailyResDTO {
    private Long id;

    private int point_earned;
    private int spark_earned;

    private int learned_count;

    private UserResponseDTO user;

    private LocalDate createdAt;
}
