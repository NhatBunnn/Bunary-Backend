package com.bunary.vocab.post.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bunary.vocab.post.dto.response.MediaResDTO;
import com.bunary.vocab.post.model.Media;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    // To dto
    MediaResDTO toDto(Media media);

    // to dto list
    List<MediaResDTO> toDtoList(List<Media> medias);

}
