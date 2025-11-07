package com.bunary.vocab.dto.reponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResDTO {
    public List<WordSetReponseDTO> wordSets;
    private long totalWordSets;

    public List<UserResponseDTO> users;
    private long totalUsers;
}
