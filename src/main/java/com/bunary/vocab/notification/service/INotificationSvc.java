package com.bunary.vocab.notification.service;

import org.springframework.data.domain.Pageable;

import com.bunary.vocab.notification.dto.param.NotificationParam;
import com.bunary.vocab.notification.dto.request.NotificationReqDTO;
import com.bunary.vocab.notification.dto.response.NotificationListResDTO;

public interface INotificationSvc {
    void notifyFollowers(NotificationParam param);

    NotificationListResDTO findAllNotificationByCurrentUser(Pageable pageable);

    void markAllNotificationsAsRead();

    void notifySystemToAll(NotificationReqDTO notificationReqDTO);

    void notifyUser(NotificationParam notificationParam);
}
