package com.bunary.vocab.notification.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class NotificationReqDTO {
    // private UUID receiverId;

    // private NotificationType type;
    // private TargetType targetType;
    // private String targetId;

    private String title;
    private String message;
}
