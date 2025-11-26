package com.bunary.vocab.mapper;

import com.bunary.vocab.dto.reponse.TagResDTO;
import com.bunary.vocab.dto.request.TagReqDTO;
import com.bunary.vocab.model.Tag;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TagMapper implements IMapper<Tag, TagReqDTO, TagResDTO> {
    @Override
    public Tag convertToEntity(TagReqDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        return tag;
    }

    @Override
    public TagResDTO convertToResDTO(Tag entity) {
        TagResDTO dto = new TagResDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public List<TagResDTO> convertToResDTO(List<Tag> entities) {
        return entities.stream().map(this::convertToResDTO).toList();
    }

}
