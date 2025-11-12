package com.bunary.vocab.service.term;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.WordResourceImgResDTO;
import com.bunary.vocab.dto.reponse.WordResourceResDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.TermMapper;
import com.bunary.vocab.model.WordResource;
import com.bunary.vocab.repository.WordResourceRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordResourceService implements IWordResourceService {
    private final WordResourceRepo wordResourceRepo;
    private final TermMapper termMapper;

    private static final String MEDIA_PREFIX = "/images/word_images/";

    @Override
    public List<WordResourceResDTO> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty() || keyword.trim().length() <= 2) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        List<WordResource> wordResources = this.wordResourceRepo.search(keyword);

        if (wordResources == null || wordResources.isEmpty()) {
            throw new ApiException(ErrorCode.NOT_FOUND);
        }

        List<WordResourceResDTO> wordResourceResDTOs = wordResources.stream().map((wordResource) -> {
            WordResourceResDTO wordResourceResDTO = this.termMapper.convertToResDTO(wordResource);

            if (wordResource.getWordResourceImgs() != null && !wordResource.getWordResourceImgs().isEmpty()) {
                wordResourceResDTO.setImages(
                        wordResource.getWordResourceImgs()
                                .stream()
                                .map((media) -> WordResourceImgResDTO.builder()
                                        .id(media.getId())
                                        .url(MEDIA_PREFIX + media.getUrl())
                                        .build()

                                ).toList());
            }

            return wordResourceResDTO;
        }).toList();

        return wordResourceResDTOs;
    }

}
