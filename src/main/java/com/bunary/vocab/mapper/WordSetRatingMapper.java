package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetRatingResDTO;
import com.bunary.vocab.dto.request.WordSetRatingReqDTO;
import com.bunary.vocab.model.WordSetRating;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetRatingMapper implements IMapper<WordSetRating, WordSetRatingReqDTO, WordSetRatingResDTO> {
    @Override
    public WordSetRating convertToEntity(WordSetRatingReqDTO dto) {
        WordSetRating rating = new WordSetRating();
        rating.setValue(dto.getValue());

        return rating;
    }

    @Override
    public WordSetRatingResDTO convertToResDTO(WordSetRating entity) {
        WordSetRatingResDTO ratingResDTO = new WordSetRatingResDTO();
        ratingResDTO.setId(entity.getId());
        ratingResDTO.setValue(entity.getValue());
        ratingResDTO.setCreatedAt(entity.getCreatedAt());
        ratingResDTO.setUpdatedAt(entity.getUpdatedAt());

        return ratingResDTO;
    }
}
