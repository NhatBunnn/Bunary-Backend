package com.bunary.vocab.service.wordSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.bunary.vocab.mapper.WordSetMapper;
import com.bunary.vocab.model.Word;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.service.CloudinaryService.CloudinaryService;
import com.bunary.vocab.service.word.IWordService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetService implements IWordSetService {
    private final WordSetRepository wordSetRepository;
    private final IWordService wordService;
    private final WordSetMapper wordSetMapper;
    private final UserMapper userMapper;
    private final CollectionMapper collectionMapper;
    private final CloudinaryService cloudinaryService;

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
    public WordSetReponseDTO createWordSet(WordSetRequestDTO wordSetDTO, MultipartFile file) throws Exception {
        // Wordset

        try {
            if (file != null) {
                String src = this.cloudinaryService.uploadFile(file);
                wordSetDTO.setThumbnail(src);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        WordSet wordSet = this.save(this.wordSetMapper.convertToWordSet(wordSetDTO));

        // Words

        if (wordSetDTO.getWord() != null) {
            List<Word> words = new ArrayList<>();
            words = wordSetDTO.getWord().stream().map(w -> {
                Word word = new Word();
                word.setIpa(w.getIpa());
                word.setMeaning(w.getMeaning());
                word.setPartOfSpeech(w.getPartOfSpeech());
                word.setTerm(w.getTerm());
                word.setThumbnail(w.getThumbnail());
                word.setWordSet(wordSet);
                return word;
            }).toList();
            this.wordService.saveAll(words);
        }

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

}
