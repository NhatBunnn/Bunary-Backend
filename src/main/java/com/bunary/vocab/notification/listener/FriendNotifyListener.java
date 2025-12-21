package com.bunary.vocab.notification.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bunary.vocab.notification.dto.param.NotificationParam;
import com.bunary.vocab.notification.model.enums.NotificationType;
import com.bunary.vocab.notification.model.enums.TargetType;
import com.bunary.vocab.notification.service.INotificationSvc;
import com.bunary.vocab.user.dto.event.FriendRequestSentEvent;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FriendNotifyListener {
    private final INotificationSvc notificationSvc;

    @Async
    @EventListener
    public void handleFriendRequestSent(FriendRequestSentEvent event) {

        NotificationParam param = NotificationParam.builder()
                .actor(event.getActor())
                .receiverId(event.getAddresseeId())
                .type(NotificationType.FRIEND_REQUEST)
                .targetType(TargetType.FRIENDSHIP)
                .title("Bạn có lời mời kết bạn mới")
                .message("Đã gửi lời mời kết bạn")
                .build();

        this.notificationSvc.notifyUser(param);

    }
}
