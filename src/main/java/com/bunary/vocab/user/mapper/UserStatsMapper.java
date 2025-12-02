package com.bunary.vocab.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.bunary.vocab.user.dto.request.UserStatsReqDTO;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.model.UserStats;

@Component
@Mapper(componentModel = "spring")
public interface UserStatsMapper {

    UserStatsResDTO toResponseDto(UserStats userStats);

    List<UserStatsResDTO> toResponseDtoList(List<UserStats> userStatsList);

    // request DTO -> entity
    // ignore tránh về sau lỡ thêm nhầm các field này vào request DTO sẽ không bị
    // map vào entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    UserStats toEntity(UserStatsReqDTO dto);

    // không cần ingore vì bản chất toEntityList chỉ gọi lại toEntity nhiều lần
    List<UserStats> toEntityList(List<UserStatsReqDTO> dtoList);

}
