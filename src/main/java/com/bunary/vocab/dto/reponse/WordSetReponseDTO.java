package com.bunary.vocab.dto.reponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bunary.vocab.model.enums.VisibilityEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WordSetReponseDTO {
    private Long id;

    private String title;

    private String description;

    private String thumbnail;

    private VisibilityEnum visibility;

    private UUID authorId;

    private UserResponseDTO author;

    private List<WordReponseDTO> words = new ArrayList<>();

    private List<CollectionResDTO> collections = new ArrayList<>();

    public WordSetStatResDTO stat;

    public List<UserWordsetHistoryResDTO> userLearnHistory;

}
