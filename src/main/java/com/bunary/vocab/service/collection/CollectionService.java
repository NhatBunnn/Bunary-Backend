package com.bunary.vocab.service.collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.CollectionMapper;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.Collection;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.CollectionRepository;
import com.bunary.vocab.repository.WordSetRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CollectionService implements ICollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final WordSetRepository wordSetRepository;

    @Override
    public Collection save(Collection collection) {
        return this.collectionRepository.save(collection);
    }

    @Override
    public CollectionResDTO create(CollectionReqDTO collection) {
        return this.collectionMapper
                .convertToCollectionResDTO(this.save(this.collectionMapper.covertToCollection(collection)));
    }

    @Override
    public Page<CollectionResDTO> findAllWithUser(Pageable pageable) {
        return this.collectionMapper.convertToCollectionResDTO(this.collectionRepository.findAllWithUser(pageable));
    }

    @Override
    public void addWordSetToCollection(Long collectionId, Long wordSetId) {

        boolean exists = collectionRepository.existsWordSetInCollection(collectionId, wordSetId);
        if (exists)
            throw new ApiException(ErrorCode.ALREADY_EXISTS);

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));
        WordSet wordSet = this.wordSetRepository.findById(wordSetId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        collection.getWordSets().add(wordSet);
        collectionRepository.save(collection);
    }

    @Override
    public CollectionResDTO findById(Long collectionId) {
        return this.collectionMapper.convertToCollectionResDTO(this.collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND)));
    }

}
