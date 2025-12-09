package com.bunary.vocab.learning.dto.response;

import java.time.LocalDate;

import com.bunary.vocab.dto.reponse.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
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
