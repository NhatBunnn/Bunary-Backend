package com.bunary.vocab.post.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.post.model.Media;

public interface IMediaSvc {
    List<Media> save(Long postId, List<MultipartFile> files);
}
