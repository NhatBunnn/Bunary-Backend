package com.bunary.vocab.service.WordSetRating;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.WordSetRatingResDTO;
import com.bunary.vocab.dto.request.WordSetRatingReqDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.mapper.WordSetRatingMapper;
import com.bunary.vocab.model.WordSetRating;
import com.bunary.vocab.model.WordSetStat;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.WordSetRatingRepo;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.repository.WordSetStatRepo;
import com.bunary.vocab.security.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("wordSetRatingService")
public class WordSetRatingService implements IWordSetRatingService {
    private final WordSetRatingMapper wordSetRatingMapper;
    private final WordSetRatingRepo ratingRepo;
    private final SecurityUtil securityUtil;
    private final WordSetRepository wordSetRepository;
    private final WordSetStatRepo wordSetStatRepo;
    private final UserMapper userMapper;

    @Override
    public Page<WordSetRatingResDTO> findAllByWordSetId(long wordSetId, Pageable pageable) {
        Page<WordSetRating> pages = this.ratingRepo.findAllByWordSet_Id(wordSetId, pageable);

        List<WordSetRatingResDTO> dtos = pages.stream().map((e) -> {
            WordSetRatingResDTO wDto = this.wordSetRatingMapper.convertToResDTO(e);
            wDto.setUser(this.userMapper.convertToUserResponseDTO(e.getUser()));
            return wDto;
        }).collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, pages.getTotalElements());
    }

    @Transactional
    @Override
    public WordSetRatingResDTO createOrUpdateRating(WordSetRatingReqDTO ratingReqDTO, long wordSetId) {
        if (ratingReqDTO.getValue() > 5 || ratingReqDTO.getValue() < 0) {
            throw new ApiException(ErrorCode.RATING_NOT_FOUND);
        }

        UUID userId = (UUID.fromString(this.securityUtil.getCurrentUser().get()));

        if (!this.wordSetRepository.existsById(wordSetId)) {
            throw new ApiException(ErrorCode.ID_NOT_FOUND);
        }

        WordSetRating rating = this.ratingRepo.findByWordSetIdAndUserId(wordSetId, userId);
        if (rating == null) {
            rating = new WordSetRating();
            rating.setValue(ratingReqDTO.getValue());
            rating.setComment(ratingReqDTO.getComment());
            rating.setWordSet(WordSet.builder().id(wordSetId).build());
            rating.setUser(User.builder().id(userId).build());
        } else {
            rating.setValue(ratingReqDTO.getValue());
            rating.setComment(ratingReqDTO.getComment());
        }

        WordSetRating curRating = this.ratingRepo.save(rating);

        Double avgRating = ratingRepo.findAvgRatingByWordSet(wordSetId);

        WordSetStat stat = this.wordSetStatRepo.findByWordSetId(wordSetId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));
        stat.setRatingAvg(avgRating);

        this.wordSetStatRepo.save(stat);

        return this.wordSetRatingMapper.convertToResDTO(curRating);
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
