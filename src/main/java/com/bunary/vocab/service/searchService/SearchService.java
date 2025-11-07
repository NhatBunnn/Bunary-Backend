package com.bunary.vocab.service.searchService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.SearchResDTO;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.model.QUser;
import com.bunary.vocab.model.QWordSet;
import com.bunary.vocab.service.user.IUserService;
import com.bunary.vocab.service.wordSet.IWordSetService;
import com.bunary.vocab.util.PageableUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SearchService implements ISearchService {
        private final JPAQueryFactory queryFactory;
        private final IUserService userService;
        private final IWordSetService wordSetService;

        @Override
        public SearchResDTO search(String keyword, int size) {
                Pageable pageable = PageRequest.of(0, size, Sort.unsorted());

                Page<WordSetReponseDTO> wordsetPage = this.wordSetService.searchWordSets(keyword, pageable);
                Page<UserResponseDTO> userPage = this.userService.searchUsers(keyword, pageable);

                SearchResDTO searchResDTO = SearchResDTO.builder()
                                .wordSets(wordsetPage.getContent())
                                .totalWordSets(wordsetPage.getTotalElements())
                                .users(userPage.getContent())
                                .totalUsers(userPage.getTotalElements())
                                .build();

                return searchResDTO;
        }

}
