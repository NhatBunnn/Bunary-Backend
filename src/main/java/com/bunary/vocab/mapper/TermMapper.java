package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordResourceResDTO;
import com.bunary.vocab.dto.request.TermReqDTO;
import com.bunary.vocab.model.WordResource;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TermMapper implements IMapper<WordResource, TermReqDTO, WordResourceResDTO> {
    @Override
    public WordResource convertToEntity(TermReqDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntity'");
    }

    @Override
    public WordResourceResDTO convertToResDTO(WordResource entity) {

        return WordResourceResDTO.builder()
                .id(entity.getId())
                .word(entity.getWord())
                .build();
    }

}
