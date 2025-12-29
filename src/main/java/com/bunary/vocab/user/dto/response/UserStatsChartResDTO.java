package com.bunary.vocab.user.dto.response;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsChartResDTO {
    // Learning stats
    private int learnedWordSetsCount;
    private int wordsetCreatedCount;

    // Points & rewards
    private int point;
    private int spark;

    // Date
    private LocalDate date;
}
