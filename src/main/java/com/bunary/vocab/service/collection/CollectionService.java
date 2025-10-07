package com.bunary.vocab.service.collection;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.CollectionMapper;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.Collection;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.CollectionRepository;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.user.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CollectionService implements ICollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final UserMapper userMapper;
    private final WordSetRepository wordSetRepository;
    private final IUserService userService;
    private final SecurityUtil securityUtil;

    @Override
    public Collection save(Collection collection) {
        return this.collectionRepository.save(collection);
    }

    @Override
    public CollectionResDTO create(CollectionReqDTO collectionReqDTO) {

        User user = new User();
        // nào rảnh sửa lại chỉ truyền nguyên id vào thôi đớ 1 query
        user = this.userService.findById(UUID.fromString(this.securityUtil.getCurrentUser().get())).orElseThrow();

        Collection collection = this.collectionMapper.convertToCollection(collectionReqDTO);
        collection.setUser(user);
        this.save(collection);

        CollectionResDTO collectionResDTO = this.collectionMapper.convertToCollectionResDTO(collection);
        collectionResDTO.setUser(this.userMapper.convertToUserResponseDTO(user));

        return collectionResDTO;
    }

    @Override
    public Page<CollectionResDTO> findAllWithUser(Pageable pageable) {
        Page<Collection> collections = this.collectionRepository.findAllWithUser(pageable);

        if (!collections.hasContent())
            throw new ApiException(ErrorCode.NOT_FOUND);

        List<CollectionResDTO> collectionDTOList = collections.map((c) -> {
            CollectionResDTO collectionResDTO = this.collectionMapper.convertToCollectionResDTO(c);

            if (c.getUser() != null) {
                collectionResDTO.setUser(this.userMapper.convertToUserResponseDTO(c.getUser()));
            }

            return collectionResDTO;
        }).toList();

        return new PageImpl<>(collectionDTOList, pageable, collections.getTotalElements());
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

    @Override
    public void removeCollectionAndWordSet(Long collectionId, Long wordSetId) {
        boolean exists = collectionRepository.existsWordSetInCollection(collectionId, wordSetId);
        if (!exists)
            throw new ApiException(ErrorCode.NOT_FOUND);

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));
        WordSet wordSet = this.wordSetRepository.findById(wordSetId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        collection.getWordSets().remove(wordSet);

        this.save(collection);
    }

    @Override
    public void removeCollection(Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        collection.getWordSets().clear();
        this.collectionRepository.delete(collection);
    }

    @Override
    public Page<CollectionResDTO> findAllByCurrentUser(Pageable pageable) {
        Page<Collection> collections = this.collectionRepository
                .findAllByUser_Id(UUID.fromString(this.securityUtil.getCurrentUser().get()), pageable);

        return this.collectionMapper.convertToCollectionResDTO(collections);
    }

}
