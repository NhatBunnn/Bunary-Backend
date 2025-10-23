package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetStudyResDTO;
import com.bunary.vocab.dto.request.WordSetStudyReqDTO;
import com.bunary.vocab.model.WordSetStudy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetStudyMapper implements IMapper<WordSetStudy, WordSetStudyReqDTO, WordSetStudyResDTO> {
    @Override
    public WordSetStudy convertToEntity(WordSetStudyReqDTO dto) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntity'");
    }

    @Override
    public WordSetStudyResDTO convertToResDTO(WordSetStudy entity) {
        WordSetStudyResDTO wDto = new WordSetStudyResDTO();
        wDto.setId(entity.getId());
        wDto.setStudyCount(entity.getStudyCount());
        wDto.setUpdatedAt(entity.getUpdatedAt());
        wDto.setCreatedAt(entity.getCreatedAt());

        return wDto;
    }

}
