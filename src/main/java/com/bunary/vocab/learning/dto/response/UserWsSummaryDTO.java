package com.bunary.vocab.learning.dto.response;

import java.util.List;

import com.bunary.vocab.user.dto.response.UserStatDailyResDTO;
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
        private UserStatDailyResDTO today;
        private UserStatDailyResDTO thisWeek;
        private UserStatDailyResDTO thisMonth;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public static class chartsDTO {
        private UserStatDailyResDTO today;
        private List<UserStatDailyResDTO> thisWeek;
        private List<UserStatDailyResDTO> thisMonth;
    }
}
