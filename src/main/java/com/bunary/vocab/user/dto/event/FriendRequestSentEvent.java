package com.bunary.vocab.user.dto.event;

import java.util.UUID;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestSentEvent {
    private ActorEventDTO actor;
    private UUID addresseeId;
}
