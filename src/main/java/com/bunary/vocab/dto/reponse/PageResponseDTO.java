package com.bunary.vocab.dto.reponse;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponseDTO {

    private int currentPage;

    private int totalPages;

    private long totalItems;

    public PageResponseDTO(Page page) {
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
    }

}
