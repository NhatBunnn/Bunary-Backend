package com.bunary.vocab.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.user.dto.response.FriendshipResDTO;

public interface IFriendshipSvc {

    void sendFriendRequest(UUID addresseeId);

    void unsendFriendRequest(UUID addresseeId);

    void acceptFriendRequest(UUID requesterId);

    void rejectFriendRequest(UUID requesterId);

    void removeFriend(UUID addresseeId);

    Page<FriendshipResDTO> findMyFriends(String keyword, Pageable pageable);
}
