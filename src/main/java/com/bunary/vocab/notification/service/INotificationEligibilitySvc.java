package com.bunary.vocab.notification.service;

import com.bunary.vocab.wordset.event.WordsetCreatedEvent;

public interface INotificationEligibilitySvc {
    boolean isEligibleForNotification(WordsetCreatedEvent event);
}
