package com.bunary.vocab.service.authentication;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.VerifyCodeReponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.RefreshToken;
import com.bunary.vocab.model.Role;
import com.bunary.vocab.model.Setting;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.enums.AuthProviderEnum;
import com.bunary.vocab.repository.SettingRepo;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.CustomUserDetails;
import com.bunary.vocab.security.JwtTokenProvider;
import com.bunary.vocab.security.JwtUtil;
import com.bunary.vocab.service.VerifyCode.IVerifyCodeService;
import com.bunary.vocab.service.refreshToken.IRefreshTokenService;
import com.bunary.vocab.service.role.IRoleService;
import com.bunary.vocab.service.user.IUserService;
import com.bunary.vocab.util.setting.LearningSettingsFactory;

import jakarta.transaction.Transactional;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    @Autowired
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final IRefreshTokenService refreshTokenService;
    private final IVerifyCodeService verifyCodeService;
    private final IRoleService roleService;
    private final SettingRepo settingRepo;
    private final UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Override
    public boolean verifyCode(VerifyCodeReponseDTO verifyCode) {
        AuthResponseDTO result = this.verifyCodeService.verifyCode(verifyCode);
        if (result == null)
            return false;
        return true;
    }

    @Override
    public boolean sendCode(UserRequestDTO user) {
        return this.verifyCodeService.sendCode(this.userService.findByEmail(user.getEmail()));
    }

    @Override
    @Transactional
    public AuthResponseDTO authenticateWithGoogle(String code) {

        String tokenUrl = "https://oauth2.googleapis.com/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // request Google token endpoint
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        // Lấy access token
        Map<String, Object> tokenResponse = response.getBody();
        String token = (String) tokenResponse.get("access_token");

        // Lấy thông tin user
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(token);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                userRequest,
                Map.class);

        Map<String, Object> userInfo = userResponse.getBody();

        String providerId = (String) userInfo.get("id");
        String email = (String) userInfo.get("email");
        String firstName = (String) userInfo.get("given_name");
        String lastName = (String) userInfo.get("family_name");
        String avatarUrl = (String) userInfo.get("picture");
        Boolean emailVerified = (Boolean) userInfo.get("verified_email");

        if (!emailVerified) {
            throw new ApiException(ErrorCode.AUTH_INVALID);
        }

        User user = this.userRepository.findByProviderAndProviderId(AuthProviderEnum.GOOGLE, providerId)
                .orElse(null);

        if (user == null) {

            Boolean isExits = this.userRepository.existsByEmail(email);
            if (isExits) {
                throw new ApiException(ErrorCode.EMAIL_EXISTS);
            }

            Role role = roleService.findByName("ROLE_USER");

            user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(avatarUrl);
            user.setStatus(1);
            user.setProvider(AuthProviderEnum.GOOGLE);
            user.setProviderId(providerId);
            user.getRoles().add(role);
            user.setEmailVerified(true);

            user = this.userRepository.save(user);

            Setting setting = LearningSettingsFactory.defaultFlashCard();
            setting.setUser(user);

            this.settingRepo.save(setting);
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtUtil.getRefreshTokenExpiration())
                .sameSite("None")
                .build();

        return userMapper.convertToAuthResponseDTO(user, accessToken, refreshToken, responseCookie);
    }

    @Override
    @Transactional
    public AuthResponseDTO Login(UserRequestDTO user) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User currentUser = userDetails.getUser();

            String accessToken = jwtTokenProvider.generateAccessToken(currentUser);
            String refreshToken = jwtTokenProvider.generateRefreshToken(currentUser);

            ResponseCookie responseCookie = ResponseCookie
                    .from("refresh_token", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(jwtUtil.getRefreshTokenExpiration())
                    .sameSite("None")
                    .build();

            return userMapper.convertToAuthResponseDTO(currentUser, accessToken, refreshToken, responseCookie);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.AUTH_INVALID);
        }
    }

    @Override
    @Transactional
    public boolean register(UserRequestDTO user) throws Exception {
        boolean isEmailExit = this.userService.existsByEmail(user.getEmail());

        if (isEmailExit) {
            throw new ApiException(ErrorCode.EMAIL_EXISTS);
        }

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        User currentUser = new User();
        currentUser = this.userMapper.convertToUser(user);

        Role role = roleService.findByName("ROLE_USER");
        currentUser.getRoles().add(role);

        this.userService.save(currentUser);

        this.verifyCodeService.sendCode(currentUser);

        Setting setting = LearningSettingsFactory.defaultFlashCard();
        setting.setUser(currentUser);

        this.settingRepo.save(setting);

        return true;
    }

    @Override
    @Transactional
    public AuthResponseDTO RefreshAccessToken(String refreshToken) throws Exception {
        if (refreshToken.equals(""))
            throw new ApiException(ErrorCode.AUTH_SESSION_INVALID);

        Jwt decodedJwt = this.jwtUtil.decodeToken(refreshToken);
        UUID userId = UUID.fromString(decodedJwt.getSubject());

        User currentUser = this.userRepository.findByIdJoinRolesAndPers(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        RefreshToken currentRefreshToken = this.refreshTokenService.findByRefreshTokenAndUser(refreshToken,
                currentUser);

        if (currentUser == null || currentRefreshToken == null ||
                currentRefreshToken.isRevoked() == true
                || Instant.now().isAfter(currentRefreshToken.getExpiryDate()))
            throw new ApiException(ErrorCode.AUTH_SESSION_EXPIRED);

        currentRefreshToken.setRevoked(true);
        this.refreshTokenService.update(currentRefreshToken);

        String accessToken = this.jwtTokenProvider.generateAccessToken(currentUser);
        String newRefreshToken = this.jwtTokenProvider.generateRefreshToken(currentUser);

        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtUtil.getRefreshTokenExpiration())
                .sameSite("None")
                .build();

        AuthResponseDTO authDTO = new AuthResponseDTO();
        authDTO = userMapper.convertToAuthResponseDTO(currentUser, accessToken, newRefreshToken, responseCookie);
        return authDTO;
    }

    @Override
    @Transactional
    public AuthResponseDTO Logout(String refreshToken) throws Exception {
        if (refreshToken.equals(""))
            throw new ApiException(ErrorCode.AUTH_SESSION_INVALID);

        Jwt decodedJwt = this.jwtUtil.decodeToken(refreshToken);
        UUID userId = UUID.fromString(decodedJwt.getSubject());

        User currentUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        RefreshToken currentRefreshToken = this.refreshTokenService.findByRefreshTokenAndUser(refreshToken,
                currentUser);

        if (currentUser == null || currentRefreshToken == null ||
                currentRefreshToken.isRevoked() == true
                || Instant.now().isAfter(currentRefreshToken.getExpiryDate()))
            throw new ApiException(ErrorCode.AUTH_SESSION_EXPIRED);

        currentRefreshToken.setRevoked(true);
        this.refreshTokenService.update(currentRefreshToken);

        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        AuthResponseDTO authDTO = new AuthResponseDTO();
        authDTO = userMapper.convertToAuthResponseDTO(currentUser, null, null, responseCookie);

        return authDTO;
    }

}
