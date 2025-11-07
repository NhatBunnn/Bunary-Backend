package com.bunary.vocab.service.searchService;

import com.bunary.vocab.dto.reponse.SearchResDTO;

public interface ISearchService {
    SearchResDTO search(String keyword, int size);
}
