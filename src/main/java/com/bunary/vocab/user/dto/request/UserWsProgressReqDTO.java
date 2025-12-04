package com.bunary.vocab.user.dto.request;

import java.time.Instant;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.user.model.enums.StudyModeEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserWsProgressReqDTO {

    private StudyModeEnum studyMode;

    private UserResponseDTO user;

    private WordSetReponseDTO wordSet;
}
