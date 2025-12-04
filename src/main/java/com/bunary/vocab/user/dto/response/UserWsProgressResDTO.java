package com.bunary.vocab.user.dto.response;

import java.time.Instant;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.user.model.enums.StudyModeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserWsProgressResDTO {
    private Long id;

    private StudyModeEnum studyMode;

    private int learnCount = 0;

    private Instant lastStudiedAt;

    private UserResponseDTO user;

    private WordSetReponseDTO wordSet;
}
