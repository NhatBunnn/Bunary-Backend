package com.bunary.vocab.service.WordSetComment;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.WordSetCommentResDTO;
import com.bunary.vocab.dto.request.WordSetCommentReqDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.mapper.WordSetCommentMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.WordSetComment;
import com.bunary.vocab.repository.WordSetCommentRepo;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetCommentService implements IWordSetCommentService {
    private final SecurityUtil securityUtil;
    private final WordSetCommentRepo wordSetCommentRepo;
    private final WordSetRepository wordSetRepository;
    private final WordSetCommentMapper wordSetCommentMapper;
    private final UserMapper userMapper;

    @Override
    public Page<WordSetCommentResDTO> findAllByWordSetId(Long wordSetId, Pageable pageable) {
        boolean curWordSet = wordSetRepository.existsById(wordSetId);
        if (!curWordSet)
            throw new ApiException(ErrorCode.ID_NOT_FOUND);

        Page<WordSetComment> cmtPage = this.wordSetCommentRepo.findAllByWordSet_Id(wordSetId, pageable);

        List<WordSetCommentResDTO> cmtList = cmtPage.stream().map((e) -> {
            WordSetCommentResDTO dto = this.wordSetCommentMapper.convertToResDTO(e);
            dto.setUser(this.userMapper.convertToUserResponseDTO(e.getUser()));
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(cmtList, pageable, cmtPage.getTotalElements());
    }

    @Transactional
    @Override
    public WordSetCommentResDTO createComment(WordSetCommentReqDTO dto, Long wordSetId) {
        boolean curWordSet = wordSetRepository.existsById(wordSetId);
        if (!curWordSet)
            throw new ApiException(ErrorCode.ID_NOT_FOUND);

        UUID userId = UUID.fromString(this.securityUtil.getCurrentUser().get());

        WordSetComment wordSetComment = WordSetComment.builder()
                .content(dto.getContent())
                .wordSet(WordSet.builder().id(wordSetId).build())
                .user(User.builder().id(userId).build())
                .build();

        return this.wordSetCommentMapper.convertToResDTO(
                this.wordSetCommentRepo.save(wordSetComment));
    }

    @Override
    public WordSetCommentResDTO updateComment(WordSetCommentReqDTO dto, Long wordSetId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }

}
