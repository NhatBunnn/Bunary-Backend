package com.bunary.vocab.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.user.service.IFriendshipSvc;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FriendshipController {
        private final IFriendshipSvc friendshipSvc;

        @PostMapping("/friends/request/{addresseeId}")
        public ResponseEntity<?> sendFriendRequest(@PathVariable String addresseeId) throws Exception {

                this.friendshipSvc.sendFriendRequest(UUID.fromString(addresseeId));

                return ResponseEntity.status(201)
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("Successfully")
                                                .build());
        }

        @DeleteMapping("/friends/request/{addresseeId}")
        public ResponseEntity<?> unsendFriendRequest(@PathVariable String addresseeId) throws Exception {

                this.friendshipSvc.unsendFriendRequest(UUID.fromString(addresseeId));

                return ResponseEntity.status(204)
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(204)
                                                .message("Successfully")
                                                .build());
        }

        @PostMapping("/friends/request/{requesterId}/accept")
        public ResponseEntity<?> acceptFriendRequest(@PathVariable String requesterId) throws Exception {

                this.friendshipSvc.acceptFriendRequest(UUID.fromString(requesterId));

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Successfully")
                                                .build());
        }

        @PostMapping("/friends/request/{requesterId}/reject")
        public ResponseEntity<?> rejectFriendRequest(@PathVariable String requesterId) throws Exception {

                this.friendshipSvc.rejectFriendRequest(UUID.fromString(requesterId));

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Successfully")
                                                .build());
        }

        @DeleteMapping("/friends/request/{requesterId}/remove")
        public ResponseEntity<?> removeFriend(@PathVariable String requesterId) throws Exception {

                this.friendshipSvc.removeFriend(UUID.fromString(requesterId));

                return ResponseEntity.status(204)
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(204)
                                                .message("Successfully")
                                                .build());

        }

        @GetMapping("/friends")
        public ResponseEntity<?> findMyFriends(
                        @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

                Pageable pageable = PageableUtil.createPageable(page, size, sort);

                this.friendshipSvc.findMyFriends(keyword, pageable);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Successfully")
                                                .build());
        }

}
