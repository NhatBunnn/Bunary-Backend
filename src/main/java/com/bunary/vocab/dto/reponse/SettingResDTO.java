package com.bunary.vocab.dto.reponse;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingResDTO {
    private Long id;

    private String type;

    private Map<String, Object> settings;

    private UserResponseDTO user;
}
