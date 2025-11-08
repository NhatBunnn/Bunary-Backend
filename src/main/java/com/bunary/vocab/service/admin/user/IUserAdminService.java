package com.bunary.vocab.service.admin.user;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.dto.reponse.UserResponseDTO;

public interface IUserAdminService {
    Page<UserResponseDTO> findAll(Map<String, String> params, Pageable pageable);
}
