package com.bunary.vocab.service.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.model.User;

public interface IUserService {
    User save(User user);

    User findByEmail(String email);

    List<UserResponseDTO> getAllUsers();

    List<UserResponseDTO> findAllVerifiedUsers();

    Optional<User> findById(UUID userId);

    boolean existsByEmail(String email);

    long deleteByIsEmailVerified(boolean isVerifed);

    UserResponseDTO updateUser(String userId, UserRequestDTO userDTO, MultipartFile file);

}
