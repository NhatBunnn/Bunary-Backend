package com.bunary.vocab.notification.dto.param;

import java.time.Instant;
import java.util.UUID;

import com.bunary.vocab.notification.model.enums.NotificationType;
import com.bunary.vocab.notification.model.enums.TargetType;
import com.bunary.vocab.user.dto.event.ActorEventDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationParam {
    private ActorEventDTO actor;
    private UUID receiverId;

    private NotificationType type;
    private TargetType targetType;
    private String targetId;

    private String title;
    private String message;

    private Instant createAt;

}
