package com.bunary.vocab.dto.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionResDTO {
    private Long id;
    private String name;
    private UserResponseDTO user;
}
