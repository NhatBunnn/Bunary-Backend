package com.bunary.vocab.service.collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
import com.bunary.vocab.model.Collection;

public interface ICollectionService {
    Collection save(Collection collection);

    CollectionResDTO create(CollectionReqDTO collection);

    Page<CollectionResDTO> findAllWithUser(Pageable pageable);

    void addWordSetToCollection(Long collectionId, Long wordSetId);

    CollectionResDTO findById(Long collectionId);
}
