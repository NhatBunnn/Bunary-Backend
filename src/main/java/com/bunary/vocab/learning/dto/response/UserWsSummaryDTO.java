package com.bunary.vocab.learning.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class UserWsSummaryDTO {

    private chartsDTO charts;
    private TotalsDTO totals;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public static class TotalsDTO {
        private UserWsDailyResDTO today;
        private UserWsDailyResDTO thisWeek;
        private UserWsDailyResDTO thisMonth;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public static class chartsDTO {
        private UserWsDailyResDTO today;
        private List<UserWsDailyResDTO> thisWeek;
        private List<UserWsDailyResDTO> thisMonth;
    }
}
