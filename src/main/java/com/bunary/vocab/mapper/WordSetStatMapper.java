package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetStatResDTO;
import com.bunary.vocab.dto.request.WordSetStatReqDTO;
import com.bunary.vocab.model.WordSetStat;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetStatMapper implements IMapper<WordSetStat, WordSetStatReqDTO, WordSetStatResDTO> {
    @Override
    public WordSetStat convertToEntity(WordSetStatReqDTO dto) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntity'");
    }

    @Override
    public WordSetStatResDTO convertToResDTO(WordSetStat entity) {
        WordSetStatResDTO wsDTO = WordSetStatResDTO.builder()
                .viewCount(entity.getViewCount())
                .studyCount(entity.getStudyCount())
                .ratingAvg(entity.getRatingAvg())
                .popularityScore(entity.getPopularityScore())
                .build();

        return wsDTO;
    }

}
