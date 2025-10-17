package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.SettingResDTO;
import com.bunary.vocab.dto.request.SettingReqDTO;
import com.bunary.vocab.model.Setting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SettingMapper {
    public Setting convertToEntity(SettingReqDTO lSettingReqDTO) {
        Setting learningSetting = new Setting();
        learningSetting.setType(lSettingReqDTO.getType());
        learningSetting.setSettings(lSettingReqDTO.getSettings());

        return learningSetting;
    }

    public SettingResDTO convertToResDTO(Setting learningSetting) {
        SettingResDTO lResDTO = new SettingResDTO();
        lResDTO.setId(learningSetting.getId());
        lResDTO.setType(learningSetting.getType());
        lResDTO.setSettings(learningSetting.getSettings());
        return lResDTO;
    }
}
