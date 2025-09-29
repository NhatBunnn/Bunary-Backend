package com.bunary.vocab.service.collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
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

    private final UserMapper userMapper;
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
    public Page<CollectionResDTO> findAll(Pageable pageable) {
        return this.collectionMapper.convertToCollectionResDTO(this.collectionRepository.findAll(pageable));
    }

    @Override
    public void addWordSetToCollection(Long collectionId, Long wordSetId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow();
        WordSet wordSet = this.wordSetRepository.findById(wordSetId).orElseThrow();

        collection.getWordSets().add(wordSet);
        collectionRepository.save(collection);
    }



}
