package com.bunary.vocab.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.user.dto.event.ActorEventDTO;
import com.bunary.vocab.user.dto.event.FriendRequestSentEvent;
import com.bunary.vocab.user.dto.response.FriendshipResDTO;
import com.bunary.vocab.user.model.Friendship;
import com.bunary.vocab.user.model.enums.FriendshipStatusEnum;
import com.bunary.vocab.user.repository.FriendshipRepo;
import com.bunary.vocab.user.service.IFriendshipSvc;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FriendshipSvc implements IFriendshipSvc {
    // Repository
    private final FriendshipRepo friendshipRepo;
    private final UserRepository userRepo;

    // Mapper
    private final UserMapper userMapper;

    // SecurityUtil
    private final SecurityUtil securityUtil;

    // Event Publisher
    private final ApplicationEventPublisher publisher;

    @Override
    public void sendFriendRequest(UUID addresseeId) {

        // Get current UserId
        UUID currUserId = this.securityUtil.getCurrentUserId();
        User currentUser = this.userRepo.findById(currUserId).orElse(null);

        // Get addressee and check existence
        User addressee = this.userRepo.findById(addresseeId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        // Create friendship
        Friendship friendship = Friendship.builder()
                .requester(currentUser)
                .addressee(addressee)
                .friendshipStatus(FriendshipStatusEnum.PENDING)
                .build();

        this.friendshipRepo.save(friendship);

        // Notification
        ActorEventDTO actor = ActorEventDTO.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .avatar(currentUser.getAvatar())
                .build();

        publisher.publishEvent(new FriendRequestSentEvent(actor, addresseeId));

    }

    @Override
    public void unsendFriendRequest(UUID addresseeId) {
        // Get current UserId
        UUID currUserId = this.securityUtil.getCurrentUserId();

        // Get addressee and check existence
        Friendship friendship = this.friendshipRepo.findByRequesterIdAndAddresseeId(currUserId, addresseeId)
                .orElseThrow(() -> new ApiException(ErrorCode.FRIENDSHIP_NOT_FOUND));

        // Check friendship status
        if (friendship.getFriendshipStatus() != FriendshipStatusEnum.PENDING)
            throw new ApiException(ErrorCode.FRIENDSHIP_NOT_PENDING);

        // Remove
        this.friendshipRepo.delete(friendship);
    }

    @Override
    public void acceptFriendRequest(UUID requesterId) {
        // Get current UserId
        UUID currUserId = this.securityUtil.getCurrentUserId();

        // Get addressee and check existence
        Friendship friendship = this.friendshipRepo.findByRequesterIdAndAddresseeId(requesterId, currUserId)
                .orElseThrow(() -> new ApiException(ErrorCode.FRIENDSHIP_NOT_FOUND));

        // Check friendship status
        if (friendship.getFriendshipStatus() != FriendshipStatusEnum.PENDING)
            throw new ApiException(ErrorCode.FRIENDSHIP_NOT_PENDING);

        // Update
        friendship.setFriendshipStatus(FriendshipStatusEnum.ACCEPTED);
        this.friendshipRepo.save(friendship);

    }

    @Override
    public void rejectFriendRequest(UUID requesterId) {
        // Get current UserId
        UUID currUserId = this.securityUtil.getCurrentUserId();

        // Get addressee and check existence
        Friendship friendship = this.friendshipRepo.findByRequesterIdAndAddresseeId(requesterId, currUserId)
                .orElseThrow(() -> new ApiException(ErrorCode.FRIENDSHIP_NOT_FOUND));

        // Check friendship status
        if (friendship.getFriendshipStatus() != FriendshipStatusEnum.PENDING)
            throw new ApiException(ErrorCode.FRIENDSHIP_NOT_PENDING);

        // Update
        friendship.setFriendshipStatus(FriendshipStatusEnum.REJECTED);
        this.friendshipRepo.save(friendship);
    }

    public void removeFriend(UUID addresseeId) {
        // Get current UserId
        UUID currUserId = this.securityUtil.getCurrentUserId();

        // Get addressee and check existence
        Friendship friendship = this.friendshipRepo.findBetween(currUserId, addresseeId)
                .orElseThrow(() -> new ApiException(ErrorCode.FRIENDSHIP_NOT_FOUND));

        // Remove
        this.friendshipRepo.delete(friendship);
    }

    @Override
    public Page<FriendshipResDTO> findMyFriends(String keyword, Pageable pageable) {
        // Get current UserId
        UUID currUserId = this.securityUtil.getCurrentUserId();

        Page<Friendship> friendshipPage = this.friendshipRepo.findMyFriends(currUserId, keyword, pageable);

        List<FriendshipResDTO> friendshipList = friendshipPage.stream().map((item) -> {

            UserResponseDTO addressee = this.userMapper.convertToUserResponseDTO(item.getAddressee());
            UserResponseDTO requester = this.userMapper.convertToUserResponseDTO(item.getRequester());

            return FriendshipResDTO.builder()
                    .id(item.getId())
                    .addressee(addressee)
                    .requester(requester)
                    .friendshipStatus(item.getFriendshipStatus())
                    .build();

        }).toList();

        return new PageImpl<>(friendshipList, pageable, friendshipPage.getTotalElements());
    }
}
