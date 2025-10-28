package com.bunary.vocab.service.wordSetStat;

public interface IWordSetStatService {
    void increaseView(Long wordsetId);

    void increaseStudy(Long wordsetId);
}
