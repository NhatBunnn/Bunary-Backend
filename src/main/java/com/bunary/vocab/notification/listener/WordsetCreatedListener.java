package com.bunary.vocab.notification.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bunary.vocab.notification.dto.param.NotificationParam;
import com.bunary.vocab.notification.model.enums.NotificationType;
import com.bunary.vocab.notification.model.enums.TargetType;
import com.bunary.vocab.notification.service.INotificationEligibilitySvc;
import com.bunary.vocab.notification.service.INotificationSvc;
import com.bunary.vocab.wordset.event.WordsetCreatedEvent;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WordsetCreatedListener {
    private final INotificationSvc notificationSvc;
    private final INotificationEligibilitySvc notificationEligibilitySvc;

    @Async
    @EventListener
    public void handleWordsetCreated(WordsetCreatedEvent event) {

        // Kiểm tra điều kiện gửi thông báo
        if (!this.notificationEligibilitySvc.isEligibleForNotification(event)) {
            return;
        }

        NotificationParam param = NotificationParam.builder()
                .actor(event.getActor())
                .type(NotificationType.WORDSET_CREATED)
                .targetType(TargetType.WORDSET)
                .targetId(event.getWordSet().getId().toString())
                .title("New Wordset Created")
                .message("đã tạo bộ từ vựng mới <strong>" + event.getWordSet().getTitle() + "</strong>")
                .build();

        this.notificationSvc.notifyFollowers(param);
    }

}
