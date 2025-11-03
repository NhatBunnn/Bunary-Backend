package com.bunary.vocab.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WordReponseDTO {
    private Long id;

    private String thumbnail;

    private String term;

    private String ipa;

    private String partOfSpeech;

    private String meaning;

    private WordSetReponseDTO wordSet;

}
