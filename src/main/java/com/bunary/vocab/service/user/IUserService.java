package com.bunary.vocab.service.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.model.User;

public interface IUserService {
    User save(User user);

    User findByEmail(String email);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    Page<UserResponseDTO> findAllVerifiedUsers(Pageable pageable);

    User findById(UUID userId);

    UserResponseDTO findByIdWithRoles(UUID userId);

    boolean existsByEmail(String email);

    long deleteByIsEmailVerified(boolean isVerifed);

    UserResponseDTO updateUser(String userId, UserRequestDTO userDTO, MultipartFile file);

}
