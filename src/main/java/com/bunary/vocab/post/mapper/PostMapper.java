package com.bunary.vocab.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bunary.vocab.post.dto.request.PostReqDTO;
import com.bunary.vocab.post.dto.response.PostResDTO;
import com.bunary.vocab.post.model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // To dto
    @Mapping(ignore = true, target = "user")
    PostResDTO toDto(Post post);

    // to Entity
    @Mapping(ignore = true, target = "user")
    Post toEntity(PostReqDTO postReqDTO);

}
