package com.bunary.vocab.service.WordSetRating;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.WordSetRatingResDTO;
import com.bunary.vocab.dto.request.WordSetRatingReqDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.WordSetRatingMapper;
import com.bunary.vocab.model.WordSetRating;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.WordSetRatingRepo;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("wordSetRatingService")
public class WordSetRatingService implements IWordSetRatingService {
    private final WordSetRatingMapper ratingMapper;
    private final WordSetRatingRepo ratingRepo;
    private final SecurityUtil securityUtil;
    private final WordSetRepository wordSetRepository;

    @Override
    public WordSetRatingResDTO create(WordSetRatingReqDTO ratingReqDTO, long wordSetId) {
        if (ratingReqDTO.getValue() > 5 || ratingReqDTO.getValue() < 0) {
            throw new ApiException(ErrorCode.RATING_NOT_FOUND);
        }

        UUID userId = (UUID.fromString(this.securityUtil.getCurrentUser().get()));

        if (this.wordSetRepository.existsByIdAndUserId(wordSetId, userId)) {
            throw new ApiException(ErrorCode.ID_NOT_FOUND);
        }

        if (this.ratingRepo.existsByWordSetIdAndUserId(wordSetId, userId)) {
            throw new ApiException(ErrorCode.RATING_ALREADY_EXISTS);
        }

        WordSetRating rating = new WordSetRating();
        rating.setValue(ratingReqDTO.getValue());
        rating.setWordSet(WordSet.builder().id(wordSetId).build());
        rating.setUser(User.builder().id(userId).build());

        return this.ratingMapper.convertToResDTO(
                this.ratingRepo.save(rating));
    }

    @Override
    public WordSetRatingResDTO update(WordSetRatingReqDTO ratingReqDTO, Long ratingId) {

        if (ratingReqDTO.getValue() > 5 || ratingReqDTO.getValue() < 0) {
            throw new ApiException(ErrorCode.RATING_NOT_FOUND);
        }

        WordSetRating rating = this.ratingRepo.findById(ratingId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        rating.setValue(ratingReqDTO.getValue());

        return this.ratingMapper.convertToResDTO(
                this.ratingRepo.save(rating));
    }

    @Override
    public void delete(Long ratingId) {

        WordSetRating rating = this.ratingRepo.findById(ratingId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        ratingRepo.delete(rating);
    }

    public boolean isOwner(Long ratingId) {
        UUID currentUserId = UUID.fromString(securityUtil.getCurrentUser().get());

        return ratingRepo.findById(ratingId)
                .map(r -> r.getUser().getId().equals(currentUserId))
                .orElse(false);
    }

}
