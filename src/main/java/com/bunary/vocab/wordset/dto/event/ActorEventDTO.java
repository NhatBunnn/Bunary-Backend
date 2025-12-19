package com.bunary.vocab.wordset.dto.event;

import java.util.UUID;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorEventDTO {
    private UUID id;
    private String fullName;
    private String avatar;
}
