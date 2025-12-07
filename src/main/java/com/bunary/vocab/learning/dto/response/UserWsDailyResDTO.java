package com.bunary.vocab.learning.dto.response;

import com.bunary.vocab.dto.reponse.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserWsDailyResDTO {
    private Long id;

    private int point_earned;
    private int spark_earned;

    private int learned_count;

    private UserResponseDTO user;
}
