package com.bunary.vocab.dto.reponse;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WordSetCommentResDTO {
    private Long id;

    private String content;

    private UserResponseDTO user;

    private Instant createdAt;

    private Instant updatedAt;
}
