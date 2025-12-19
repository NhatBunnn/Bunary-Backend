package com.bunary.vocab.notification.controller;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.notification.dto.request.NotificationReqDTO;
import com.bunary.vocab.notification.dto.response.NotificationListResDTO;
import com.bunary.vocab.notification.service.INotificationSvc;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {
        private final INotificationSvc notificationSvc;

        @GetMapping("/notifications/me")
        public ResponseEntity<?> findAllNotificationByCurrentUser(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

                Pageable pageable = PageableUtil.createPageable(page, size, sort);

                NotificationListResDTO result = this.notificationSvc.findAllNotificationByCurrentUser(pageable);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Successfully")
                                                .data(result)
                                                .build());
        }

        @PostMapping("/notifications/read-all")
        public ResponseEntity<?> markAllNotificationsAsRead() {

                this.notificationSvc.markAllNotificationsAsRead();

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("All notifications marked as read")
                                                .build());
        }

        @PostMapping("/notifications/system")
        public ResponseEntity<?> notifySystem(
                        @RequestBody NotificationReqDTO notificationReqDTO) {

                this.notificationSvc.notifySystemToAll(notificationReqDTO);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("All notifications sent to all users")
                                                .build());
        }

}
