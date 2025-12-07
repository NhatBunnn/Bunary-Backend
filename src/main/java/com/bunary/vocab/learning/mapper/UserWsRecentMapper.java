package com.bunary.vocab.learning.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bunary.vocab.learning.dto.response.UserWsRecentResDTO;
import com.bunary.vocab.learning.model.UserWordSetRecent;

@Mapper(componentModel = "spring")
public interface UserWsRecentMapper {
    @Mapping(target = "wordSet", ignore = true)
    UserWsRecentResDTO toResponseDto(UserWordSetRecent entity);

    @Mapping(target = "wordSet", ignore = true)
    List<UserWsRecentResDTO> toResponseDtoList(List<UserWordSetRecent> entityList);

}
