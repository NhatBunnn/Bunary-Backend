package com.bunary.vocab.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserMapper {

    public AuthResponseDTO convertToAuthResponseDTO(User user, String accessToken, String refreshToken,
            ResponseCookie responseCookie) {
        AuthResponseDTO authDTO = new AuthResponseDTO();
        authDTO.setUser(convertToUserResponseDTO(user));
        authDTO.setAccessToken(accessToken);
        authDTO.setRefreshToken(refreshToken);
        authDTO.setResponseCookie(responseCookie);

        return authDTO;

    }

    public UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setFullName(user.getFullName());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setGender(user.getGender());

        return userDTO;
    }

    public List<UserResponseDTO> convertToUserResponseDTO(List<User> users) {
        List<UserResponseDTO> userReponseList = new ArrayList<>();

        for (User user : users) {
            userReponseList.add(this.convertToUserResponseDTO(user));
        }

        return userReponseList;
    }

    public UserRequestDTO convertToUserRequestDTO(User user) {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setGender(user.getGender());
        userDTO.updateFullName();

        return userDTO;
    }

    public User convertToUser(UserRequestDTO userDTO) {
        User user = new User();

        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        return user;
    }

}
