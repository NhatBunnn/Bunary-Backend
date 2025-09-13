package com.bunary.vocab.service.authentication;

import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.VerifyCodeReponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;

public interface IAuthService {
    boolean verifyCode(VerifyCodeReponseDTO verifyCode);

    boolean sendCode(UserRequestDTO user);

    boolean register(UserRequestDTO user) throws Exception;

    AuthResponseDTO Login(UserRequestDTO user) throws Exception;

    AuthResponseDTO RefreshAccessToken(String refreshToken) throws Exception;

    AuthResponseDTO Logout(String refreshToken) throws Exception;
}
