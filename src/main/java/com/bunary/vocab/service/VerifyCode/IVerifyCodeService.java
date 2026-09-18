package com.bunary.vocab.service.VerifyCode;

import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.VerifyCodeReponseDTO;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.VerifyCode;

/* 
    // LEGACY CODE
     * Old OTP verification flow using database.
     * Replaced by Redis-based OTP verification.
     */

public interface IVerifyCodeService {
    String generate6Digits();

    boolean sendCode(User user);

    AuthResponseDTO verifyCode(VerifyCodeReponseDTO verifyCode);

    void delete(VerifyCode verifyCode);

}
