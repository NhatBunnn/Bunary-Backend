package com.bunary.vocab.service.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.model.User;
import com.bunary.vocab.profile.dto.response.ProfileResDTO;
import com.bunary.vocab.profile.mapper.ProfileMapper;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.CloudinaryService.CloudinaryService;
import com.bunary.vocab.user.model.Friendship;
import com.bunary.vocab.user.model.enums.FriendshipStatusEnum;
import com.bunary.vocab.user.repository.FriendshipRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    // Repository
    private final UserRepository userRepository;
    private final FriendshipRepo friendshipRepo;
    // Mapper
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;

    private final CloudinaryService cloudinaryService;
    private final SecurityUtil securityUtil;

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDTO findById(UUID userId) {
        // Get current user
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        return this.userMapper.convertToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO findByUsername(String username) {
        // Get current user
        UUID curUserId = this.securityUtil.getCurrentUserId();

        // Get user
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Friendship friendship = this.friendshipRepo.findBetween(curUserId, user.getId())
                .orElse(null);

        // Convert to DTO
        UserResponseDTO userResponseDTO = this.userMapper.convertToUserResponseDTO(user);
        ProfileResDTO profileResDTO = this.profileMapper.toResponseDto(user.getProfile());

        FriendshipStatusEnum status;
        if (friendship == null) {
            status = FriendshipStatusEnum.NONE;
        } else if (friendship.getFriendshipStatus() == FriendshipStatusEnum.ACCEPTED) {
            status = FriendshipStatusEnum.ACCEPTED;
        } else {
            if (friendship.getRequester().getId().equals(curUserId)) {
                status = FriendshipStatusEnum.PENDING_SENT;
            } else {
                status = FriendshipStatusEnum.PENDING_RECEIVED;
            }
        }

        userResponseDTO.setProfile(profileResDTO);
        userResponseDTO.setFriendshipStatus(status);

        return userResponseDTO;

    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public long deleteByIsEmailVerified(boolean isVerifed) {
        return this.userRepository.deleteByIsEmailVerified(isVerifed);
    }

    @Override
    public UserResponseDTO updateUser(String userId, UserRequestDTO userDTO, MultipartFile avatarFile) {

        User user = this.userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }

        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }

        if (avatarFile != null) {
            try {
                String url = cloudinaryService.uploadFile(avatarFile);
                user.setAvatar(url);
            } catch (IOException e) {
                throw new RuntimeException("Upload avatar failed", e);
            }
        }

        if (userDTO.getAddress() != null) {
            user.setAddress(userDTO.getAddress());
        }

        if (userDTO.getDateOfBirth() != null) {
            user.setDateOfBirth(userDTO.getDateOfBirth());
        }

        if (userDTO.getGender() != null) {
            user.setGender(userDTO.getGender());
        }

        this.save(user);

        return this.userMapper.convertToUserResponseDTO(user);
    }

    @Override
    public Page<UserResponseDTO> searchUsers(String keyword, Pageable pageable) {
        Page<User> usersPage = this.userRepository.search(keyword, pageable);

        List<UserResponseDTO> users = usersPage.stream()
                .map(u -> this.userMapper.convertToUserResponseDTO(u))
                .collect(Collectors.toList());

        return new PageImpl<>(
                users,
                pageable,
                usersPage.getTotalElements());
    }

    @Override
    @Transactional
    public Page<UserResponseDTO> findAllSuggestions(int pageSize) {

        UUID curUserId = this.securityUtil.getCurrentUserId();

        Pageable pageable = PageRequest.of(0, pageSize);

        long userCount = this.userRepository.countForSuggestion(curUserId);

        if (userCount > pageable.getPageSize()) {

            int maxOffset = (int) (userCount - pageable.getPageSize());
            int randomOffset = ThreadLocalRandom.current().nextInt(0, maxOffset + 1);
            int randomPage = randomOffset / pageable.getPageSize();

            pageable = pageable.withPage(randomPage);
        }

        Page<User> usersPage = this.userRepository.findAll(curUserId, pageable);

        List<UserResponseDTO> users = usersPage.stream()
                .map(u -> this.userMapper.convertToUserResponseDTO(u))
                .collect(Collectors.toList());

        return new PageImpl<>(users, pageable, usersPage.getTotalElements());
    }

}
