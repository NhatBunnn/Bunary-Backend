package com.bunary.vocab.notification.dto.response;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.bunary.vocab.notification.model.enums.NotificationType;
import com.bunary.vocab.notification.model.enums.TargetType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResDTO {

    @Id
    private String id;

    private UUID receiverId;

    private Actor actor;

    private NotificationType type;

    private TargetType targetType;
    private String targetId;

    private String title;
    private String message;

    private boolean isRead;
    private Instant createdAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Actor {
        private UUID id;
        private String fullName;
        private String avatar;
    }
}
