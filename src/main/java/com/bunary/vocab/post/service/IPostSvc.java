package com.bunary.vocab.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.post.dto.request.PostReqDTO;
import com.bunary.vocab.post.dto.response.PostResDTO;

public interface IPostSvc {
    PostResDTO create(PostReqDTO postReqDTO, List<MultipartFile> files);

    Page<PostResDTO> findAllByCurrentUser(Pageable pageable);
}
