package com.bunary.vocab.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
import com.bunary.vocab.model.Collection;
import com.bunary.vocab.model.User;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CollectionMapper {
    private final SecurityUtil securityUtil;
    private final UserMapper userMapper;
    private final WordSetMapper wordSetMapper;

    public CollectionResDTO convertToCollectionResDTO(Collection collection) {
        CollectionResDTO collectionResDTO = new CollectionResDTO();
        collectionResDTO.setId(collection.getId());
        collectionResDTO.setName(collection.getName());
        collectionResDTO.setUser(this.userMapper.convertToUserResponseDTO(collection.getUser()));

        return collectionResDTO;
    }

    public Collection covertToCollection(CollectionReqDTO collectionReqDTO) {

        User user = new User();
        user.setId(UUID.fromString(this.securityUtil.getCurrentUser().get()));

        Collection collection = new Collection();
        collection.setName(collectionReqDTO.getName());
        collection.setUser(user);

        return collection;
    }

    public Page<CollectionResDTO> convertToCollectionResDTO(Page<Collection> collection) {
        List<CollectionResDTO> CollectionList = new ArrayList<>();

        for (Collection c : collection) {
            CollectionList.add(this.convertToCollectionResDTO(c));
        }

        return new PageImpl<>(CollectionList, collection.getPageable(), collection.getTotalElements());
    }
}
