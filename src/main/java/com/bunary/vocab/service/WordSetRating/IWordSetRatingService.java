package com.bunary.vocab.service.WordSetRating;

import com.bunary.vocab.dto.reponse.WordSetRatingResDTO;
import com.bunary.vocab.dto.request.WordSetRatingReqDTO;

public interface IWordSetRatingService {
    WordSetRatingResDTO create(WordSetRatingReqDTO ratingReqDTO, long wordSetId);

    WordSetRatingResDTO update(WordSetRatingReqDTO ratingReqDTO, Long ratingId);

    void delete(Long ratingId);

}
