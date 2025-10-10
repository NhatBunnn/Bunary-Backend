package com.bunary.vocab.service.wordSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.CollectionMapper;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.mapper.WordMapper;
import com.bunary.vocab.mapper.WordSetMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.Word;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.enums.VisibilityEnum;
import com.bunary.vocab.repository.WordRepository;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.CloudinaryService.CloudinaryService;
import com.bunary.vocab.service.user.IUserService;
import com.bunary.vocab.service.word.IWordService;

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
    private final IUserService userService;
    private final SecurityUtil securityUtil;
    private final WordRepository wordRepository;

    @Override
    public WordSet save(WordSet wordSet) {
        return this.wordSetRepository.save(wordSet);
    }

    @Override
    public Page<WordSet> findAllEntities(Pageable pageable) {
        return this.wordSetRepository.findAll(pageable);
    }

    @Override
    public Page<WordSetReponseDTO> findAll(Pageable pageable) {
        return this.wordSetMapper.convertToWordSetReponseDTO(this.findAllEntities(pageable));
    }

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

        return this.wordSetMapper.convertToWordSetReponseDTO(currentWordSet);

    }

    @Override
    public WordSetReponseDTO createWordSet(WordSetRequestDTO wordSetDTO, MultipartFile file) throws Exception {
        // Wordset
        WordSet wordSet = this.wordSetMapper.convertToWordSet(wordSetDTO);
        if (wordSetDTO.getVisibility() == null) {
            wordSet.setVisibility(VisibilityEnum.PRIVATE);
        }

        Optional<User> user = Optional.of(new User());
        user = this.userService.findById(UUID.fromString(this.securityUtil.getCurrentUser().get()));

        wordSet.setUser(user.get());

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

        this.save(wordSet);

        return this.wordSetMapper.convertToWordSetReponseDTO(wordSet);
    }

    @Override
    public WordSetReponseDTO findById(Long id) {
        Optional<WordSet> wordSet = this.wordSetRepository.findById(id);

        return this.wordSetMapper.convertToWordSetReponseDTO(wordSet.get());
    }

    @Override
    public Page<WordSetReponseDTO> findAllByCollectionId(Long collectionId,
            Pageable pageable) {
        return this.wordSetMapper
                .convertToWordSetReponseDTO(this.wordSetRepository.findByCollections_Id(collectionId, pageable));
    }

    @Override
    public WordSetReponseDTO findByIdWithUserAndCollection(Long id) {
        WordSet wordSet = this.wordSetRepository.findByIdWithUserAndCollection(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        WordSetReponseDTO wordSetDTO = this.wordSetMapper.convertToWordSetReponseDTO(wordSet);
        wordSetDTO.setAuthor(this.userMapper.convertToUserResponseDTO(wordSet.getUser()));
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
        VisibilityEnum visibilityEnum = VisibilityEnum.valueOf(visibility.toUpperCase());
        Page<WordSet> page = this.wordSetRepository.findAllByVisibilityWithUser(visibilityEnum, pageable);

        List<WordSetReponseDTO> dtoList = page.stream().map(ws -> {
            WordSetReponseDTO dto = wordSetMapper.convertToWordSetReponseDTO(ws);

            if (ws.getUser() != null) {
                dto.setAuthor(this.userMapper.convertToUserResponseDTO(ws.getUser()));
            }

            return dto;
        }).toList();

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

}
