package com.bunary.vocab.learning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bunary.vocab.learning.dto.response.UserWsDailyResDTO;
import com.bunary.vocab.learning.model.UserWordSetDaily;

@Mapper(componentModel = "spring")
public interface UserWsDailyMapper {

    @Mapping(target = "user", ignore = true)
    UserWsDailyResDTO toResponseDto(UserWordSetDaily entity);
}
