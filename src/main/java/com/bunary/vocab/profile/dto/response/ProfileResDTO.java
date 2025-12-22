package com.bunary.vocab.profile.dto.response;

import com.bunary.vocab.dto.reponse.UserResponseDTO;

import lombok.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResDTO {
    private Long id;

    private String banner;

    private String title;

    private String bio;

    private UserResponseDTO user;

}
