package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.exception.GlobalErrorCode;
import com.bunary.vocab.exception.CustomException.BadRequestException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.scheduler.AccountCleanupTask;
import com.bunary.vocab.service.user.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")

// @RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;
    private final AccountCleanupTask accountCleanupTask;
    private final UserMapper userMapper;

    @GetMapping("/removeaccount")
    public String removeAccount() {
        long result = this.accountCleanupTask.removeAccount();

        return "đã xóa " + result;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String id) throws Exception {
        User currentUser = this.userService.findById(UUID.fromString(id)).orElseThrow(
                () -> new BadRequestException(GlobalErrorCode.USER_NOT_FOUND));
        UserResponseDTO result = this.userMapper.convertToUserResponseDTO(currentUser);

        return ResponseEntity.ok()
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("ok rồi"), result));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @ModelAttribute UserRequestDTO requestDTO,
            @RequestParam(value = "avatarFile", required = false) MultipartFile file)
            throws Exception {

        UserResponseDTO result = this.userService.updateUser(id, requestDTO, file);

        return ResponseEntity.ok()
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("ok rồi"), result));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

        String sortField = sort[0];
        Sort.Direction direction;

        if (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<UserResponseDTO> result = this.userService.findAllVerifiedUsers(pageable);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(202)
                        .message(List.of("ok rồi"))
                        .data(result.getContent())
                        .pagination(new PageResponseDTO(result))
                        .build());
    }
}
