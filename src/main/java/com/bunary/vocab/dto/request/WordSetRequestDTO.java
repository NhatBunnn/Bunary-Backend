package com.bunary.vocab.dto.request;

import java.util.List;

import com.bunary.vocab.model.Word;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordSetRequestDTO {

    private String title;

    private String description;

    private String thumbnail;

    private String author;

    private List<WordRequestDTO> word;

    private List<Long> removedWordIds;
}
