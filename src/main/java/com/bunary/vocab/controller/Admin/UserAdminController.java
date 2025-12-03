package com.bunary.vocab.controller.Admin;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.service.admin.user.IUserAdminService;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

@RequestMapping("/api/v1/admin")
@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {
    private final IUserAdminService userAdminService;

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers(
            @RequestParam(required = false) Map<String, String> params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

        Pageable pageable = PageableUtil.createPageable(page, size, sort);

        Page<UserResponseDTO> result = this.userAdminService.findAll(params, pageable);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(202)
                        .message("Users retrieved successfully")
                        .data(result.getContent())
                        .pagination(new PageResponseDTO(result))
                        .build());
    }
}
