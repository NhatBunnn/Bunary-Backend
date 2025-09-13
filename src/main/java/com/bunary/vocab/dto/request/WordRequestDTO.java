package com.bunary.vocab.dto.request;

import com.bunary.vocab.model.WordSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WordRequestDTO {
    private String thumbnail;

    private String term;

    private String ipa;

    private String partOfSpeech;

    private String meaning;

    private WordSet wordSet;

}
