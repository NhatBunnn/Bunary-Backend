package com.bunary.vocab.batchapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FinishWordSetResDTO {
    private int point;
    private int spark;
}
