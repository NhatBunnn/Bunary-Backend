package com.bunary.vocab.profile.service.impl;

import java.security.Security;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.profile.dto.request.ProfileReqDTO;
import com.bunary.vocab.profile.dto.response.ProfileResDTO;
import com.bunary.vocab.profile.mapper.ProfileMapper;
import com.bunary.vocab.profile.model.Profile;
import com.bunary.vocab.profile.repository.ProfileRepo;
import com.bunary.vocab.profile.service.IProfileSvc;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileSvc implements IProfileSvc {
    // Repository
    private final ProfileRepo profileRepo;
    private final UserRepository userRepo;

    // Mapper
    private final ProfileMapper profileMapper;

    // Sercutity
    private final SecurityUtil securityUtil;

    @Override
    public ProfileResDTO update(ProfileReqDTO profileReqDTO) {
        // Get current user Id
        UUID userId = securityUtil.getCurrentUserId();

        // Update profile
        Profile profile = profileRepo.findByUserId(userId).orElse(null);

        if (profileReqDTO.getBanner() != null)
            profile.setBanner(profileReqDTO.getBanner());
        if (profileReqDTO.getTitle() != null)
            profile.setTitle(profileReqDTO.getTitle());
        if (profileReqDTO.getBio() != null)
            profile.setBio(profileReqDTO.getBio());

        profileRepo.save(profile);

        return this.profileMapper.toResponseDto(profile);
    }

}
