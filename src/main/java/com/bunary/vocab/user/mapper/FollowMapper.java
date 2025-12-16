package com.bunary.vocab.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bunary.vocab.user.dto.response.FollowResDTO;
import com.bunary.vocab.user.model.Follow;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    // entity -> response DTO

    @Mapping(target = "follower", ignore = true)
    @Mapping(target = "followee", ignore = true)
    FollowResDTO toResponseDto(Follow follow);
}
