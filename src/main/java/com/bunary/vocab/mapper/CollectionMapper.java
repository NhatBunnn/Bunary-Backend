package com.bunary.vocab.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
import com.bunary.vocab.model.Collection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CollectionMapper {
    private final UserMapper userMapper;

    public CollectionResDTO convertToCollectionResDTO(Collection collection) {
        CollectionResDTO collectionResDTO = new CollectionResDTO();
        collectionResDTO.setId(collection.getId());
        collectionResDTO.setName(collection.getName());

        return collectionResDTO;
    }

    public Collection covertToCollection(CollectionReqDTO collectionReqDTO) {
        Collection collection = new Collection();
        collection.setName(collectionReqDTO.getName());

        return collection;
    }

    public Page<CollectionResDTO> convertToCollectionResDTO(Page<Collection> collection) {
        List<CollectionResDTO> CollectionList = new ArrayList<>();

        for (Collection c : collection) {
            CollectionList.add(this.convertToCollectionResDTO(c));
        }

        return new PageImpl<>(CollectionList, collection.getPageable(), collection.getTotalElements());
    }

    public List<CollectionResDTO> convertToCollectionResDTO(List<Collection> collection) {
        List<CollectionResDTO> CollectionList = new ArrayList<>();

        for (Collection c : collection) {
            CollectionList.add(this.convertToCollectionResDTO(c));
        }

        return CollectionList;
    }
}
