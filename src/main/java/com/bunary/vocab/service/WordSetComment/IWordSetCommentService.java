package com.bunary.vocab.service.WordSetComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.dto.reponse.WordSetCommentResDTO;
import com.bunary.vocab.dto.request.WordSetCommentReqDTO;

public interface IWordSetCommentService {

    Page<WordSetCommentResDTO> findAllByWordSetId(Long wordSetId, Pageable pageable);

    WordSetCommentResDTO createComment(WordSetCommentReqDTO dto, Long wordSetId);

    WordSetCommentResDTO updateComment(WordSetCommentReqDTO dto, Long wordSetId);
}
