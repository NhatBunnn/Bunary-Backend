package com.bunary.vocab.notification.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.model.User;
import com.bunary.vocab.notification.dto.param.NotificationParam;
import com.bunary.vocab.notification.dto.request.NotificationReqDTO;
import com.bunary.vocab.notification.dto.response.NotificationListResDTO;
import com.bunary.vocab.notification.dto.response.NotificationResDTO;
import com.bunary.vocab.notification.mapper.NotificationMapper;
import com.bunary.vocab.notification.model.Notification;
import com.bunary.vocab.notification.model.enums.NotificationType;
import com.bunary.vocab.notification.model.enums.TargetType;
import com.bunary.vocab.notification.repository.NotificationRepo;
import com.bunary.vocab.notification.service.INotificationSvc;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.user.dto.event.ActorEventDTO;
import com.bunary.vocab.user.repository.FollowRepo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationSvc implements INotificationSvc {
        // Repository
        private final FollowRepo followRepo;
        private final NotificationRepo notificationRepo;
        private final UserRepository userRepository;

        // Mapper
        private final NotificationMapper notificationMapper;

        // Util
        private final SecurityUtil securityUtil;

        // Messaging
        private final SimpMessagingTemplate messagingTemplate;

        @Override
        @Transactional
        public void notifyFollowers(NotificationParam notificationParam) {
                List<UUID> followerIds = this.followRepo
                                .findAllFolloweeIdByFollowerId(notificationParam.getActor().getId());

                List<Notification> notifications = followerIds.stream().map(followerId -> {
                        return Notification.builder()
                                        .actor(Notification.Actor.builder()
                                                        .id(notificationParam.getActor().getId())
                                                        .fullName(notificationParam.getActor().getFullName())
                                                        .avatar(notificationParam.getActor().getAvatar())
                                                        .build())
                                        .receiverId(followerId)
                                        .type(notificationParam.getType())
                                        .targetType(notificationParam.getTargetType())
                                        .targetId(notificationParam.getTargetId())
                                        .title(notificationParam.getTitle())
                                        .message(notificationParam.getMessage())
                                        .isRead(false)
                                        .createdAt(Instant.now())
                                        .build();
                }).toList();

                this.notificationRepo.saveAll(notifications);

                notifications.forEach((notification) -> {
                        NotificationResDTO notificationDto = this.notificationMapper.toResponseDto(notification);

                        messagingTemplate.convertAndSendToUser(
                                        notificationDto.getReceiverId().toString(),
                                        "/queue/notifications",
                                        notificationDto);

                });

        }

        public void notifyUser(NotificationParam notificationParam) {

                Notification notification = Notification.builder()
                                .actor(Notification.Actor.builder()
                                                .id(notificationParam.getActor().getId())
                                                .fullName(notificationParam.getActor().getFullName())
                                                .avatar(notificationParam.getActor().getAvatar())
                                                .build())
                                .receiverId(notificationParam.getReceiverId())
                                .type(notificationParam.getType())
                                .targetType(notificationParam.getTargetType())
                                .targetId(notificationParam.getTargetId())
                                .title(notificationParam.getTitle())
                                .message(notificationParam.getMessage())
                                .isRead(false)
                                .createdAt(Instant.now())
                                .build();

                // save
                this.notificationRepo.save(notification);

                // Send notification to user
                NotificationResDTO notificationDto = this.notificationMapper.toResponseDto(notification);

                messagingTemplate.convertAndSendToUser(
                                notificationDto.getReceiverId().toString(),
                                "/queue/notifications",
                                notificationDto);
        }

        @Override
        public void notifySystemToAll(NotificationReqDTO notificationReqDTO) {
                UUID currentUserId = securityUtil.getCurrentUserId();
                User user = userRepository.findById(currentUserId).orElseThrow();

                // Create actor && receiver
                ActorEventDTO actor = ActorEventDTO.builder()
                                .id(user.getId())
                                .fullName(user.getFullName())
                                .avatar(user.getAvatar())
                                .build();

                List<User> receivers = this.userRepository.findAll();

                // Create notification
                List<Notification> notifications = receivers.stream().map(
                                receiver -> {
                                        return Notification.builder()
                                                        .receiverId(receiver.getId())
                                                        .actor(
                                                                        Notification.Actor.builder()
                                                                                        .id(actor.getId())
                                                                                        .fullName(actor.getFullName())
                                                                                        .avatar(actor.getAvatar())
                                                                                        .build())
                                                        .type(NotificationType.SYSTEM)
                                                        .targetType(TargetType.NONE)
                                                        .title(notificationReqDTO.getTitle())
                                                        .message(notificationReqDTO.getMessage())
                                                        .isRead(false)
                                                        .createdAt(Instant.now())
                                                        .build();
                                }).toList();

                this.notificationRepo.saveAll(notifications);

                // Send notification to all receivers
                notifications.forEach((notification) -> {
                        NotificationResDTO notificationDto = this.notificationMapper.toResponseDto(notification);

                        messagingTemplate.convertAndSendToUser(
                                        notificationDto.getReceiverId().toString(),
                                        "/queue/notifications",
                                        notificationDto);

                });
        }

        @Override
        public NotificationListResDTO findAllNotificationByCurrentUser(Pageable pageable) {
                UUID currentUserId = securityUtil.getCurrentUserId();

                Page<Notification> notifications = this.notificationRepo.findAllByReceiverId(currentUserId, pageable);

                List<NotificationResDTO> notificationDtos = notifications.stream()
                                .map(notification -> notificationMapper.toResponseDto(notification))
                                .toList();

                NotificationListResDTO notificationListResDTO = NotificationListResDTO.builder()
                                .unReadCount(this.notificationRepo.countAllByReceiverIdAndIsReadFalse(currentUserId))
                                .notification(notificationDtos)
                                .pagination(new PageResponseDTO(
                                                new PageImpl<>(notificationDtos, pageable,
                                                                notifications.getTotalElements())))
                                .build();

                return notificationListResDTO;
        }

        @Override
        public void markAllNotificationsAsRead() {
                UUID currentUserId = securityUtil.getCurrentUserId();

                List<Notification> unreadNotifications = this.notificationRepo
                                .findAllByReceiverIdAndIsReadFalse(currentUserId);

                if (unreadNotifications.isEmpty()) {
                        return;
                }

                unreadNotifications.forEach(notification -> notification.setRead(true));

                this.notificationRepo.saveAll(unreadNotifications);
        }

}
