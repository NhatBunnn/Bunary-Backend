package com.bunary.vocab.post.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.post.model.Media;
import com.bunary.vocab.post.model.enums.MediaTargetType;
import com.bunary.vocab.post.model.enums.MediaType;
import com.bunary.vocab.post.service.IMediaSvc;
import com.bunary.vocab.service.CloudinaryService.CloudinaryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaSvc implements IMediaSvc {
    // Repository

    // Mapper

    // Service
    private final CloudinaryService cloudinarySvc;
    // Util

    private static final int MAX_MEDIA = 3;

    @Override
    public List<Media> save(Long postId, List<MultipartFile> files) {

        if (files == null || files.isEmpty())
            return List.of();

        if (files.size() > MAX_MEDIA) {
            throw new ApiException(ErrorCode.POST_MAX_3_IMAGES_EXCEEDED);
        }

        List<Media> medias = cloudinarySvc.uploadFiles(files).stream().map((url) -> {
            return Media.builder()
                    .url(url)
                    .targetType(MediaTargetType.POST)
                    .mediaType(MediaType.IMAGE)
                    .targetId(postId)
                    .build();
        }).toList();

        return medias;
    }
}
