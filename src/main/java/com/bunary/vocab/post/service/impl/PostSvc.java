package com.bunary.vocab.post.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.post.dto.request.PostReqDTO;
import com.bunary.vocab.post.dto.response.MediaResDTO;
import com.bunary.vocab.post.dto.response.PostResDTO;
import com.bunary.vocab.post.mapper.MediaMapper;
import com.bunary.vocab.post.mapper.PostMapper;
import com.bunary.vocab.post.model.Media;
import com.bunary.vocab.post.model.Post;
import com.bunary.vocab.post.model.enums.MediaTargetType;
import com.bunary.vocab.post.repository.MediaRepo;
import com.bunary.vocab.post.repository.PostRepo;
import com.bunary.vocab.post.service.IMediaSvc;
import com.bunary.vocab.post.service.IPostSvc;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostSvc implements IPostSvc {
    // Repository
    private final PostRepo postRepo;
    private final MediaRepo mediaRepo;
    private final UserRepository userRepo;
    // Mapper
    private final PostMapper postMapper;
    private final MediaMapper mediaMapper;
    private final UserMapper userMapper;

    // Service
    private final IMediaSvc mediaSvc;

    // Util
    private final SecurityUtil securityUtil;

    // @Override
    public PostResDTO create(PostReqDTO postReqDTO, List<MultipartFile> files) {
        // Get user
        UUID userId = this.securityUtil.getCurrentUserId();
        User user = User.builder().id(userId).build();

        // Validate
        if (files.size() > 4) {
            throw new ApiException(ErrorCode.POST_MAX_3_IMAGES_EXCEEDED);
        }

        // Post
        Post post = this.postMapper.toEntity(postReqDTO);
        post.setUser(user);
        this.postRepo.save(post);

        // Media
        List<Media> medias = this.mediaSvc.save(post.getId(), files);
        this.mediaRepo.saveAll(medias);

        // Convert to DTO
        PostResDTO postResDTO = this.postMapper.toDto(post);
        List<MediaResDTO> mediaResDTOs = this.mediaMapper.toDtoList(medias);

        postResDTO.setMedias(mediaResDTOs);

        return postResDTO;
    }

    public Page<PostResDTO> findAllByCurrentUser(Pageable pageable) {

        // Get user
        UUID userId = this.securityUtil.getCurrentUserId();
        User user = this.userRepo.findById(userId).orElse(null);
        UserResponseDTO userResponseDTO = this.userMapper.convertToUserResponseDTO(user);

        // Find all posts by user
        Page<Post> posts = this.postRepo.findAllByCurrentUserId(userId, pageable);

        List<Long> postIds = posts.getContent().stream().map(Post::getId).toList();

        if (postIds.size() == 0) {
            return null;
        }

        // find all medias by post id
        Page<Media> medias = this.mediaRepo.findByTargetTypeAndTargetIdIn(MediaTargetType.POST, postIds, pageable);

        // Convert to DTO
        List<PostResDTO> postResDTOs = posts.stream().map(this.postMapper::toDto).toList();

        // Associate media to post
        if (postResDTOs.size() > 0) {
            for (PostResDTO postResDTO : postResDTOs) {

                List<MediaResDTO> mediaResDTOsByPost = medias
                        .stream()
                        .filter(media -> media.getTargetId() == postResDTO.getId())
                        .map(this.mediaMapper::toDto)
                        .toList();

                postResDTO.setMedias(mediaResDTOsByPost);
                postResDTO.setUser(userResponseDTO);
            }
        }

        return new PageImpl<>(postResDTOs, pageable, posts.getTotalElements());
    }
}
