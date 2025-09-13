package com.bunary.vocab.dto.reponse;

import java.util.List;
import java.util.UUID;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WordSetReponseDTO {
    private Long id;

    private String title;

    private String description;

    private String thumbnail;

    private UUID authorId;

    private UserResponseDTO author;

    private List<WordReponseDTO> words;

    public WordSetReponseDTO(Long id, String title, String description, String thumbnail, UUID authorId,
            UserResponseDTO author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.authorId = authorId;
        this.author = author;
    }

}
