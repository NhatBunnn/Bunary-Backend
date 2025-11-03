package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetCommentResDTO;
import com.bunary.vocab.dto.request.WordSetCommentReqDTO;
import com.bunary.vocab.model.WordSetComment;

@Service
public class WordSetCommentMapper implements IMapper<WordSetComment, WordSetCommentReqDTO, WordSetCommentResDTO> {

    @Override
    public WordSetComment convertToEntity(WordSetCommentReqDTO dto) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntity'");
    }

    @Override
    public WordSetCommentResDTO convertToResDTO(WordSetComment entity) {
        return WordSetCommentResDTO.builder()
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
