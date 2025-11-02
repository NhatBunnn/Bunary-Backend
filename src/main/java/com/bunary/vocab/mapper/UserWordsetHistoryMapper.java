package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.UserWordsetHistoryResDTO;
import com.bunary.vocab.model.UserWordsetHistory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserWordsetHistoryMapper {

    public UserWordsetHistoryResDTO convertToResDTO(UserWordsetHistory entity) {
        return UserWordsetHistoryResDTO.builder()
                .id(entity.getId())
                .lastLearnedAt(entity.getLastLearnedAt())
                .build();
    }

}
