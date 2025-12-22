package com.bunary.vocab.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bunary.vocab.profile.dto.response.ProfileResDTO;
import com.bunary.vocab.profile.model.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    // entity -> resDto
    @Mapping(target = "user", ignore = true)
    ProfileResDTO toResponseDto(Profile profile);

}
