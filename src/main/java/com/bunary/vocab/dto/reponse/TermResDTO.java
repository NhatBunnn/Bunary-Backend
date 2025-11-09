package com.bunary.vocab.dto.reponse;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TermResDTO {
    private Long id;

    private String word;

    private List<TermMediaResDTO> termMedias;
}
