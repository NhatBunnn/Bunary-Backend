package com.bunary.vocab.notification.mapper;

import org.mapstruct.Mapper;

import com.bunary.vocab.notification.dto.response.NotificationResDTO;
import com.bunary.vocab.notification.model.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResDTO toResponseDto(Notification entity);
}
