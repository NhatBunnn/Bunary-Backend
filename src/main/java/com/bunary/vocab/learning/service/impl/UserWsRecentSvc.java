package com.bunary.vocab.learning.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.learning.dto.response.UserWsRecentResDTO;
import com.bunary.vocab.learning.mapper.UserWsRecentMapper;
import com.bunary.vocab.learning.model.QUserWordSetRecent;
import com.bunary.vocab.learning.model.UserWordSetRecent;
import com.bunary.vocab.learning.repository.UserWsRecentRepo;
import com.bunary.vocab.learning.service.IUserWsRecentSvc;
import com.bunary.vocab.mapper.WordSetMapper;
import com.bunary.vocab.model.QWordSet;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserWsRecentSvc implements IUserWsRecentSvc {
    // Repository
    private final WordSetRepository wordSetRepository;
    private final UserWsRecentRepo userWsRecentRepo;

    // Mapper
    private final UserWsRecentMapper userWsRecentMapper;
    private final WordSetMapper wordSetMapper;

    // Security
    private final SecurityUtil securityUtil;

    // QueryDSL
    private final JPAQueryFactory queryFactory;

    @Transactional
    @Override
    public void record(Long wordsetId) {
        if (!this.wordSetRepository.existsById(wordsetId)) {
            throw new ApiException(ErrorCode.ID_NOT_FOUND);
        }

        UUID currentUserId = UUID.fromString(securityUtil.getCurrentUser().get());

        UserWordSetRecent userWordSetRecent = this.userWsRecentRepo.findByWordSetIdAndUserId(wordsetId,
                currentUserId).orElse(null);

        if (userWordSetRecent == null) {
            userWordSetRecent = new UserWordSetRecent();
            userWordSetRecent.setLastLearnedAt(Instant.now());

            User user = User.builder().id(currentUserId).build();
            userWordSetRecent.setUser(user);

            WordSet wordSet = WordSet.builder().id(wordsetId).build();
            userWordSetRecent.setWordSet(wordSet);
        } else {
            userWordSetRecent.setLastLearnedAt(Instant.now());
        }

        this.userWsRecentRepo.save(userWordSetRecent);
    }

    @Transactional
    @Override
    public Page<UserWsRecentResDTO> findAllByCurrentUser(Map<String, String> params, Pageable pageable) {
        UUID currUserId = securityUtil.getCurrentUserId();

        QWordSet q_WordSet = QWordSet.wordSet;
        QUserWordSetRecent q_UserWordSetRecent = QUserWordSetRecent.userWordSetRecent;

        /**
         * Dynamic filter
         */
        BooleanBuilder builder = new BooleanBuilder();

        // Filter keyword
        String keyword = params.get("keyword");
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(q_WordSet.title.lower().like("%" + keyword.toLowerCase() + "%"));
        }

        // Sort
        OrderSpecifier<?> order = q_UserWordSetRecent.lastLearnedAt.desc();
        String sort = params.get("sort");
        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            String field = parts[0];
            String dir = parts.length > 1 ? parts[1] : "desc";

            boolean asc = dir.equalsIgnoreCase("asc");

            switch (field.toLowerCase()) {
                case "lastLearnedat" -> order = asc ? q_UserWordSetRecent.lastLearnedAt.asc()
                        : q_UserWordSetRecent.lastLearnedAt.desc();
            }
        }

        // Fetch data
        List<UserWordSetRecent> userWordSetRecents = queryFactory
                .select(q_UserWordSetRecent)
                .from(q_UserWordSetRecent)
                .leftJoin(q_UserWordSetRecent.wordSet, q_WordSet)
                .where(builder.and(q_UserWordSetRecent.user.id.eq(currUserId)))
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(q_UserWordSetRecent.count())
                .from(q_UserWordSetRecent)
                .leftJoin(q_UserWordSetRecent.wordSet, q_WordSet)
                .where(builder.and(q_UserWordSetRecent.user.id.eq(currUserId)))
                .fetchOne();

        // Convert to DTO
        List<UserWsRecentResDTO> dtos = userWordSetRecents.stream().map((uwsr) -> {
            UserWsRecentResDTO dto = this.userWsRecentMapper.toResponseDto(uwsr);
            WordSetReponseDTO wSetReponseDTO = this.wordSetMapper.convertToWordSetReponseDTO(uwsr.getWordSet());
            dto.setWordSet(wSetReponseDTO);
            return dto;
        }).toList();

        return new PageImpl<>(dtos, pageable, total);
    }

}
