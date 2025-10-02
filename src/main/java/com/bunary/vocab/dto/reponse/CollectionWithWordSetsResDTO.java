package com.bunary.vocab.dto.reponse;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionWithWordSetsResDTO {
    private CollectionResDTO collection;
    private Page<WordSetReponseDTO> wordSets;
}
