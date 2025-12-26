package com.bunary.vocab.post.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.post.dto.request.PostReqDTO;
import com.bunary.vocab.post.dto.response.PostResDTO;
import com.bunary.vocab.post.service.IPostSvc;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final IPostSvc postSvc;

    @GetMapping("/posts/me")
    public ResponseEntity<?> findAllByCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

        Pageable pageable = PageableUtil.createPageable(page, 20, sort);

        Page<PostResDTO> result = this.postSvc.findAllByCurrentUser(pageable);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Successfully")
                        .data(result.getContent())
                        .pagination(new PageResponseDTO(result))
                        .build());
    }

    @PostMapping("/posts")
    public ResponseEntity<?> create(
            @RequestPart("post") PostReqDTO postReqDTO,
            @RequestPart(value = "mediaFile", required = false) List<MultipartFile> file) throws Exception {

        PostResDTO result = this.postSvc.create(postReqDTO, file);

        return ResponseEntity.status(201)
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("Successfully")
                        .data(result)
                        .build());
    }

}
