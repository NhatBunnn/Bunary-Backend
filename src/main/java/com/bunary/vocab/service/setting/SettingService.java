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
    public SettingResDTO findByTypeAndCurrentUser(String type) {
        UUID userId = UUID.fromString(this.securityUtil.getCurrentUser().get());

        Setting setting = this.settingRepo.findByUserIdAndType(userId, type);

        return this.settingMapper.convertToResDTO(setting);
    }

    @Override
    public SettingResDTO save(SettingReqDTO LReqDTO) {
        Setting setting = this.settingMapper.convertToEntity(LReqDTO);

        User user = new User();
        user.setId(UUID.fromString(this.securityUtil.getCurrentUser().get()));

        setting.setUser(user);

        this.settingRepo.save(setting);

        return this.settingMapper.convertToResDTO(setting);
    }

    @Override
    public SettingResDTO update(SettingReqDTO settingReqDTO) {
        UUID userId = UUID.fromString(this.securityUtil.getCurrentUser().get());
        Setting setting = this.settingRepo.findByUserIdAndType(userId, settingReqDTO.getType());

        if (setting == null)
            throw new ApiException(ErrorCode.NOT_FOUND);

        Setting newSetting = this.settingMapper.convertToEntity(settingReqDTO);
        newSetting.setId(setting.getId());

        User user = new User();
        user.setId(userId);
        newSetting.setUser(user);

        this.settingRepo.save(newSetting);

        return this.settingMapper.convertToResDTO(newSetting);

    }

}
