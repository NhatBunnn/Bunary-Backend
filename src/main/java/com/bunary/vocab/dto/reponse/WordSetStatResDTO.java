package com.bunary.vocab.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WordSetStatResDTO {
    private Long id;

    private WordSetReponseDTO wordSet;

    private Long viewCount;

    private int studyCount;

    private Long wordCount;

    private double ratingAvg;

    private double popularityScore;
}
