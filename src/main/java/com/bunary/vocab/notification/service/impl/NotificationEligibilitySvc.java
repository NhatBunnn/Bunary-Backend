package com.bunary.vocab.notification.service.impl;

import com.bunary.vocab.model.enums.VisibilityEnum;
import com.bunary.vocab.notification.service.INotificationEligibilitySvc;
import com.bunary.vocab.wordset.event.WordsetCreatedEvent;

import org.springframework.stereotype.Service;

@Service
public class NotificationEligibilitySvc implements INotificationEligibilitySvc {

    @Override
    public boolean isEligibleForNotification(WordsetCreatedEvent event) {

        // Wordset phải có chế độ công khai
        if (event.getWordSet().getVisibility() != VisibilityEnum.PUBLIC) {
            return false;
        }

        return true;
    }
}
