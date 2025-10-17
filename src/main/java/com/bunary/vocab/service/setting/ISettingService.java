package com.bunary.vocab.service.setting;

import com.bunary.vocab.dto.reponse.SettingResDTO;
import com.bunary.vocab.dto.request.SettingReqDTO;

public interface ISettingService {
    SettingResDTO findLearningSettingByModeAndCurrentUser(String mode);

    SettingResDTO save(SettingReqDTO settingReqDTO);
}
