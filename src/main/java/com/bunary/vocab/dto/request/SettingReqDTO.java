package com.bunary.vocab.dto.request;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingReqDTO {
    private String type;

    private Map<String, Object> settings;

}
