package com.bunary.vocab.user.dto.response;

import java.util.List;

import com.bunary.vocab.user.dto.response.enums.StatsPeriodEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Builder
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UserStatDailyResDTO {
    private StatsPeriodEnum period;
    private UserStatsTotalResDTO total;
    private List<UserStatsChartResDTO> chart;
}
