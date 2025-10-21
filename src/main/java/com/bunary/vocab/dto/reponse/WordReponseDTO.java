package com.bunary.vocab.dto.reponse;

import com.bunary.vocab.model.WordSet;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
