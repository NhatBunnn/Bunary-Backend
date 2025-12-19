package com.bunary.vocab.wordset.dto.event;

import com.bunary.vocab.model.enums.VisibilityEnum;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordSetEventDTO {
    private Long id;

    private String title;

    private VisibilityEnum visibility;
}
