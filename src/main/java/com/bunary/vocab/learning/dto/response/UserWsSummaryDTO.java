package com.bunary.vocab.learning.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserWsSummaryDTO {

    private UserWsDailyResDTO today;
    private List<UserWsDailyResDTO> thisWeek;
    private List<UserWsDailyResDTO> thisMonth;
    private TotalsDTO totals;

    @Data
    @Builder
    public static class TotalsDTO {
        private UserWsDailyResDTO thisWeek;
        private UserWsDailyResDTO thisMonth;
    }
}
