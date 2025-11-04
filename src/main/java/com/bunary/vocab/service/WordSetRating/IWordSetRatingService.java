package com.bunary.vocab.service.WordSetRating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.dto.reponse.WordSetRatingResDTO;
import com.bunary.vocab.dto.request.WordSetRatingReqDTO;

public interface IWordSetRatingService {
    WordSetRatingResDTO createOrUpdateRating(WordSetRatingReqDTO ratingReqDTO, long wordSetId);

    void delete(Long ratingId);

    Page<WordSetRatingResDTO> findAllByWordSetId(long wordSetId, Pageable pageable);

}
