package com.bunary.vocab.service.user;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.model.User;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.CloudinaryService.CloudinaryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
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
    public Optional<User> findById(UUID userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return this.userMapper.convertToUserResponseDTO(this.userRepository.findAll(pageable));
    }

    @Override
    public Page<UserResponseDTO> findAllVerifiedUsers(Pageable pageable) {
        UUID userId = UUID.fromString(this.securityUtil.getCurrentUser().get());
        return this.userMapper
                .convertToUserResponseDTO(this.userRepository.findAllVerifiedUsers(pageable, userId));
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

        User user = this.findById(UUID.fromString(userId)).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));

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

}
