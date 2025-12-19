package com.bunary.vocab.notification.dto.response;

import java.util.List;

import com.bunary.vocab.dto.reponse.PageResponseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationListResDTO {
    private long unReadCount;
    private List<NotificationResDTO> notification;
    private PageResponseDTO pagination;
}
