package com.bunary.vocab.service.WordSetRating;

import com.bunary.vocab.dto.reponse.WordSetRatingResDTO;
import com.bunary.vocab.dto.request.WordSetRatingReqDTO;

public interface IWordSetRatingService {
    WordSetRatingResDTO createOrUpdateRating(WordSetRatingReqDTO ratingReqDTO, long wordSetId);

    void delete(Long ratingId);

}
