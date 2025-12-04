package com.bunary.vocab.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bunary.vocab.user.dto.request.UserWsProgressReqDTO;
import com.bunary.vocab.user.dto.response.UserWsProgressResDTO;
import com.bunary.vocab.user.model.UserWordSetProgress;

@Mapper(componentModel = "spring")
public interface UserWsProgressMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "wordSet", ignore = true)
    UserWsProgressResDTO toResponseDto(UserWordSetProgress userWordSetProgress);

    List<UserWsProgressResDTO> toResponseDtoList(List<UserWordSetProgress> userWordSetProgresses);

    // request DTO -> entity
    // ignore tránh về sau lỡ thêm nhầm các field này vào request DTO sẽ không bị
    // map vào entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    UserWordSetProgress toEntity(UserWsProgressReqDTO dto);

    // không cần ingore vì bản chất toEntityList chỉ gọi lại toEntity nhiều lần
    List<UserWordSetProgress> toEntityList(List<UserWsProgressReqDTO> dtoList);
}
