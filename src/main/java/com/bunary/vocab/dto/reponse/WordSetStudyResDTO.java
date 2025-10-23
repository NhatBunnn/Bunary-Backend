package com.bunary.vocab.dto.reponse;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSetStudyResDTO {
    private Long id;

    private int studyCount;

    private Instant createdAt;

    private Instant updatedAt;

    private WordReponseDTO wordReponseDTO;

    private UserResponseDTO userResponseDTO;
}
