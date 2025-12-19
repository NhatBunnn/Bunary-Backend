package com.bunary.vocab.notification.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bunary.vocab.notification.model.Notification;

public interface NotificationRepo extends MongoRepository<Notification, String> {
    Page<Notification> findAllByReceiverId(UUID receiverId, Pageable pageable);

    List<Notification> findAllByReceiverIdAndIsReadFalse(UUID receiverId);

    long countAllByReceiverIdAndIsReadFalse(UUID receiverId);
}
