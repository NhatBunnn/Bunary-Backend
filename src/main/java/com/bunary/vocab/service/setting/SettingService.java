package com.bunary.vocab.service.setting;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.SettingResDTO;
import com.bunary.vocab.dto.request.SettingReqDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.SettingMapper;
import com.bunary.vocab.model.Setting;
import com.bunary.vocab.model.User;
import com.bunary.vocab.repository.SettingRepo;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SettingService implements ISettingService {
    private final SettingMapper settingMapper;
    private final SecurityUtil securityUtil;
    private final SettingRepo settingRepo;

    @Override
    public SettingResDTO findLearningSettingByModeAndCurrentUser(String mode) {
        UUID userId = UUID.fromString(this.securityUtil.getCurrentUser().get());

        Setting learningSetting = this.settingRepo.findByUserIdAndTypeAndMode(userId, "learning", mode)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));
        ;
        return this.settingMapper.convertToResDTO(learningSetting);
    }

    @Override
    public SettingResDTO save(SettingReqDTO LReqDTO) {
        Setting learningSetting = this.settingMapper.convertToEntity(LReqDTO);

        User user = new User();
        user.setId(UUID.fromString(this.securityUtil.getCurrentUser().get()));

        learningSetting.setUser(user);

        this.settingRepo.save(learningSetting);

        return this.settingMapper.convertToResDTO(learningSetting);
    }

}
