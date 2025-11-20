package com.bunary.vocab.service.wordSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.reponse.UserWordsetHistoryResDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.reponse.WordSetStatResDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.CollectionMapper;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.mapper.UserWordsetHistoryMapper;
import com.bunary.vocab.mapper.WordMapper;
import com.bunary.vocab.mapper.WordSetMapper;
import com.bunary.vocab.mapper.WordSetStatMapper;
import com.bunary.vocab.model.QUser;
import com.bunary.vocab.model.QUserWordsetHistory;
import com.bunary.vocab.model.QWordSet;
import com.bunary.vocab.model.QWordSetStat;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.Word;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.WordSetStat;
import com.bunary.vocab.model.enums.VisibilityEnum;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.repository.WordRepository;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.repository.WordSetStatRepo;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.CloudinaryService.CloudinaryService;
import com.bunary.vocab.service.user.IUserService;
import com.bunary.vocab.util.wordSet.WordSetStatCalculator;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetService implements IWordSetService {
    private final WordSetRepository wordSetRepository;
    private final WordSetMapper wordSetMapper;
    private final WordMapper wordMapper;
    private final UserMapper userMapper;
    private final CollectionMapper collectionMapper;
    private final CloudinaryService cloudinaryService;
    private final SecurityUtil securityUtil;
    private final WordRepository wordRepository;
    private final WordSetStatRepo wordSetStatRepo;
    private final WordSetStatMapper wordSetStatMapper;
    private final UserRepository userRepository;
    private final UserWordsetHistoryMapper userWordsetHistoryMapper;

    private final JPAQueryFactory queryFactory;

    @Override
    public WordSet save(WordSet wordSet) {
        return this.wordSetRepository.save(wordSet);
    }

    @Override
    public Page<WordSet> findAllEntities(Pageable pageable) {
        return this.wordSetRepository.findAll(pageable);
    }

    // @Override
    // public Page<WordSetReponseDTO> findAll(Pageable pageable) {
    // return
    // this.wordSetMapper.convertToWordSetReponseDTO(this.findAllEntities(pageable));
    // }

    @Override
    public Page<WordSetReponseDTO> findAllWithAuthor(Pageable pageable) {
        Page<WordSet> page = this.wordSetRepository.findAllWithAuthor(pageable);

        List<WordSetReponseDTO> dtoList = page.stream().map(ws -> {
            WordSetReponseDTO dto = wordSetMapper.convertToWordSetReponseDTO(ws);

            if (ws.getUser() != null) {
                dto.setAuthor(this.userMapper.convertToUserResponseDTO(ws.getUser()));
            }

            return dto;
        }).toList();

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public WordSetReponseDTO update(WordSetRequestDTO wordSetDTO, Long wordSetId, MultipartFile file) throws Exception {

        WordSet wordSet = this.wordSetRepository.findByIdWithWords(wordSetId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
        wordSet.setTitle(wordSetDTO.getTitle());
        wordSet.setDescription(wordSetDTO.getDescription());
        if (wordSetDTO.getVisibility() == null) {
            wordSet.setVisibility(VisibilityEnum.PRIVATE);
        }
        wordSet.setVisibility(wordSetDTO.getVisibility());

        try {
            if (file != null) {
                String src = this.cloudinaryService.uploadFile(file);
                wordSet.setThumbnail(src);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (wordSetDTO.getRemovedWordIds() != null && !wordSetDTO.getRemovedWordIds().isEmpty()) {
            List<Word> words = this.wordRepository.findAllById(wordSetDTO.getRemovedWordIds());
            wordSet.getWords().removeAll(words);
        }

        if (wordSetDTO.getWord() != null && !wordSetDTO.getWord().isEmpty()) {
            List<Long> wordsId = wordSetDTO.getWord()
                    .stream()
                    .filter(w -> w.getId() != null)
                    .map(w -> w.getId())
                    .toList();

            if (!wordsId.isEmpty()) {
                long count = wordSetRepository.countWordsInWordSet(wordSetId, wordsId);

                if (count != wordsId.size()) {
                    throw new ApiException(ErrorCode.NOT_FOUND);
                }
            }

            // Cách phổ biến đển so sánh ID entiy và ID dto
            Map<Long, Word> existingWords = wordSet.getWords().stream()
                    .collect(Collectors.toMap(Word::getId, w -> w));

            List<Word> words = new ArrayList<>();
            words = wordSetDTO.getWord().stream().map((w) -> {
                Word word;
                if (w.getId() != null) {
                    word = existingWords.get(w.getId());
                    word.setIpa(w.getIpa());
                    word.setMeaning(w.getMeaning());
                    word.setPartOfSpeech(w.getPartOfSpeech());
                    word.setTerm(w.getTerm());
                    word.setThumbnail(w.getThumbnail());
                } else {
                    word = this.wordMapper.convertToWord(w);
                    word.setWordSet(wordSet);
                }

                return word;
            }).collect(Collectors.toCollection(ArrayList::new));
            /*
             * .toList() trả về immutable list.
             * Hibernate muốn mutable list để quản lý @OneToMany.
             * .collect(Collectors.toCollection(ArrayList::new)) → mutable list, Hibernate
             * thao tác bình thường
             * ( chưa hiểu lắm nào xem lại )
             */

            // wordSet.setWords(words);
            wordSet.getWords().addAll(words);
        }

        WordSet currentWordSet = this.save(wordSet);

        // update words count tin
        WordSetStat stat = this.wordSetStatRepo.findByWordSetId(wordSetId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));
        stat.setWordCount((long) wordSetDTO.getWord().size());
        this.wordSetStatRepo.save(stat);

        return this.wordSetMapper.convertToWordSetReponseDTO(currentWordSet);

    }

    @Override
    public WordSetReponseDTO createWordSet(WordSetRequestDTO wordSetDTO, MultipartFile file) throws Exception {
        // Wordset
        WordSet wordSet = this.wordSetMapper.convertToWordSet(wordSetDTO);
        if (wordSetDTO.getVisibility() == null) {
            wordSet.setVisibility(VisibilityEnum.PRIVATE);
        }
        User user = this.userRepository.findById(UUID.fromString(this.securityUtil.getCurrentUser().get()))
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        wordSet.setUser(user);

        try {
            if (file != null) {
                String src = this.cloudinaryService.uploadFile(file);
                wordSet.setThumbnail(src);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Word> words = new ArrayList<>();
        if (wordSetDTO.getWord() != null) {
            words = wordSetDTO.getWord().stream().map((w) -> {
                Word word = this.wordMapper.convertToWord(w);
                word.setWordSet(wordSet);
                return word;
            }).toList();
        }

        wordSet.setWords(words);

        WordSet curWordSet = this.save(wordSet);

        // update words count tin
        WordSetStat stat = new WordSetStat();
        stat.setViewCount((long) 0);
        stat.setStudyCount(0);
        stat.setRatingAvg(0);
        stat.setPopularityScore(0);
        stat.setWordCount((long) wordSetDTO.getWord().size());
        stat.setWordSet(curWordSet);

        this.wordSetStatRepo.save(stat);

        return this.wordSetMapper.convertToWordSetReponseDTO(curWordSet);
    }

    @Override
    public Page<WordSetReponseDTO> findAllByCollectionId(Long collectionId,
            Pageable pageable) {
        return this.wordSetMapper
                .convertToWordSetReponseDTO(this.wordSetRepository.findByCollections_Id(collectionId, pageable));
    }

    @Override
    public WordSetReponseDTO findById(Long id) {

        WordSet wordSet = this.wordSetRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        UUID userId = UUID.fromString(securityUtil.getCurrentUser().get());

        if (wordSet.getVisibility().equals(VisibilityEnum.PRIVATE)) {
            if (!wordSet.getUser().getId().equals(userId)) {
                throw new ApiException(ErrorCode.FORBIDDEN);
            }
        }

        WordSetReponseDTO wordSetDTO = this.wordSetMapper.convertToWordSetReponseDTO(wordSet);
        wordSetDTO.setAuthor(this.userMapper.convertToUserResponseDTO(wordSet.getUser()));
        wordSetDTO.setStat(this.wordSetStatMapper.convertToResDTO(wordSet.getWordSetStat()));
        wordSetDTO.getCollections().addAll(this.collectionMapper.convertToCollectionResDTO(wordSet.getCollections()));

        return wordSetDTO;
    }

    @Override
    public WordSetReponseDTO findByIdWithWords(Long id) {
        WordSet wordSet = this.wordSetRepository.findByIdWithWords(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        WordSetReponseDTO wordSetDTO = this.wordSetMapper.convertToWordSetReponseDTO(wordSet);
        wordSetDTO.getWords().addAll(this.wordMapper.convertToWordReponseDTO(wordSet.getWords()));

        return wordSetDTO;
    }

    @Override
    public Page<WordSetReponseDTO> findAllByVisibilityWithUser(String visibility, Pageable pageable) {
        VisibilityEnum visibilityEnum = VisibilityEnum.valueOf(visibility);

        Page<WordSet> page = this.wordSetRepository.findAllByVisibilityWithUser(visibilityEnum, pageable);

        List<WordSetReponseDTO> dtoList = page.stream().map(ws -> {
            WordSetReponseDTO dto = wordSetMapper.convertToWordSetReponseDTO(ws);

            dto.setAuthor(this.userMapper.convertToUserResponseDTO(ws.getUser()));

            return dto;
        }).toList();

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public Page<WordSetReponseDTO> findAllByCurrentUser(Pageable pageable) {
        Page<WordSet> page = this.wordSetRepository
                .findAllByUserId(UUID.fromString(this.securityUtil.getCurrentUser().get()), pageable);

        List<WordSetReponseDTO> list = page.stream().map(ws -> {
            WordSetReponseDTO wordSetReponseDTO = this.wordSetMapper.convertToWordSetReponseDTO(ws);
            return wordSetReponseDTO;
        }).toList();
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public void removeWordSet(Long wordsetId) {
        WordSet wordSet = this.wordSetRepository.findById(wordsetId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        if (wordSet.getCollections() != null)
            wordSet.getCollections().clear();
        if (wordSet.getWords() != null)
            wordSet.getWords().clear();

        this.wordSetRepository.delete(wordSet);
    }

    @Transactional
    public void recalculateAllPopularityScores() {
        List<WordSetStat> allStats = wordSetStatRepo.findAll();

        for (WordSetStat stat : allStats) {
            double popularity = WordSetStatCalculator.calculatePopularityScore(stat);
            stat.setPopularityScore(popularity);
        }

        wordSetStatRepo.saveAll(allStats);
    }

    public WordSetStat createDefaultStat(WordSet wordSet) {
        WordSetStat stat = new WordSetStat();
        stat.setViewCount((long) 0);
        stat.setStudyCount(0);
        stat.setRatingAvg(0);
        stat.setPopularityScore(0);
        stat.setWordSet(wordSet);
        return this.wordSetStatRepo.save(stat);
    }

    @Transactional
    @Override
    public Page<WordSetReponseDTO> findAll(Map<String, String> params, Pageable pageable) {

        QWordSet ws = QWordSet.wordSet;
        QUser u = QUser.user;
        QWordSetStat s = QWordSetStat.wordSetStat;

        // --- build dynamic filter ---
        BooleanBuilder builder = new BooleanBuilder();

        // filter keyword
        String keyword = params.get("keyword");
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(ws.title.lower().like("%" + keyword.toLowerCase() + "%"));
        }

        // filter visibility
        String visibilityStr = params.get("visibility");
        if (visibilityStr != null && !visibilityStr.isEmpty()) {
            try {
                VisibilityEnum visibilityEnum = VisibilityEnum.valueOf(visibilityStr.toUpperCase());
                builder.and(ws.visibility.eq(visibilityEnum));
            } catch (IllegalArgumentException ignored) {
            }
        }

        // --- build dynamic sort ---
        OrderSpecifier<?> order = ws.createdAt.desc(); // default sort
        String sort = params.get("sort");
        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            String field = parts[0];
            String dir = parts.length > 1 ? parts[1] : "desc";

            switch (field.toLowerCase()) {
                case "popularityscore":
                    order = dir.equalsIgnoreCase("desc") ? s.popularityScore.desc() : s.popularityScore.asc();
                    break;
                case "createdat":
                    order = dir.equalsIgnoreCase("desc") ? ws.createdAt.desc() : ws.createdAt.asc();
                    break;
                case "studycount":
                    order = dir.equalsIgnoreCase("desc") ? s.studyCount.desc() : s.studyCount.asc();
                    break;
                case "viewcount":
                    order = dir.equalsIgnoreCase("desc") ? s.viewCount.desc() : s.viewCount.asc();
                    break;
            }
        }

        // --- query list ---
        List<WordSetReponseDTO> list = queryFactory
                .select(Projections.bean(
                        WordSetReponseDTO.class,
                        ws.id, ws.title, ws.description, ws.thumbnail, ws.visibility,
                        Projections.bean(UserResponseDTO.class, u.fullName, u.avatar).as("author"),
                        Projections.bean(WordSetStatResDTO.class,
                                s.popularityScore, s.ratingAvg, s.viewCount, s.wordCount).as("stat")))
                .from(ws)
                .leftJoin(ws.wordSetStat, s)
                .leftJoin(ws.user, u)
                .where(builder)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // --- query total ---
        long total = queryFactory
                .select(ws.count())
                .from(ws)
                .leftJoin(ws.wordSetStat, s)
                .leftJoin(ws.user, u)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(list, pageable, total);
    }

    @Transactional
    public Page<WordSetReponseDTO> findAllRecentWordSetsByCurrentUser(Map<String, String> params, Pageable pageable) {

        UUID userId = UUID.fromString(this.securityUtil.getCurrentUser().get());

        QWordSet ws = QWordSet.wordSet;
        QUser u = QUser.user;
        QUserWordsetHistory uwh = QUserWordsetHistory.userWordsetHistory;

        // build filter
        BooleanBuilder builder = new BooleanBuilder();

        // filter
        String keyword = params.get("keyword");
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(ws.title.lower().like("%" + keyword.toLowerCase() + "%"));
        }

        // sort
        OrderSpecifier<?> order = uwh.lastLearnedAt.desc();
        String sort = params.get("sort");
        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            String field = parts[0];
            String dir = parts.length > 1 ? parts[1] : "desc";

            boolean asc = dir.equalsIgnoreCase("asc");
            switch (field.toLowerCase()) {
                case "lastLearnedat" -> order = asc ? uwh.lastLearnedAt.asc() : uwh.lastLearnedAt.desc();
            }
        }

        List<WordSet> wordSets = queryFactory
                .selectFrom(ws)
                .leftJoin(ws.userWordsetHistories, uwh)
                .leftJoin(uwh.user, u)
                .where(builder.and(u.id.eq(userId)))
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(ws.count())
                .from(ws)
                .leftJoin(ws.userWordsetHistories, uwh)
                .leftJoin(uwh.user, u)
                .where(builder.and(u.id.eq(userId)))
                .fetchOne();

        List<WordSetReponseDTO> dtos = wordSets.stream().map((wsItem) -> {
            WordSetReponseDTO wordSetReponseDTO = this.wordSetMapper.convertToWordSetReponseDTO(wsItem);
            wordSetReponseDTO.setUserLearnHistory(
                    this.userWordsetHistoryMapper.convertToResDTO(wsItem.getUser().getUserWordsetHistories()));
            return wordSetReponseDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, total);
    }

    @Override
    public Page<WordSetReponseDTO> searchWordSets(String keyword, Pageable pageable) {
        Page<WordSet> wordsetsPage = this.wordSetRepository.searchWithVisibility(keyword, VisibilityEnum.PUBLIC,
                pageable);

        List<WordSetReponseDTO> wordsets = wordsetsPage.stream()
                .map(ws -> this.wordSetMapper.convertToWordSetReponseDTO(ws))
                .collect(Collectors.toList());

        return new PageImpl<>(
                wordsets,
                pageable,
                wordsetsPage.getTotalElements());
    }

}
