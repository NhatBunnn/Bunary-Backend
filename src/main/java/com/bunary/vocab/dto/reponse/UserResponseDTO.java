package com.bunary.vocab.dto.reponse;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.bunary.vocab.model.enums.GenderEnum;
import com.bunary.vocab.profile.dto.response.ProfileResDTO;
import com.bunary.vocab.user.model.enums.FriendshipStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private String username;
    private String nickname;
    private String avatar;
    private String address;
    private Instant dateOfBirth;
    private GenderEnum gender;

    private ProfileResDTO profile;

    private FriendshipStatusEnum friendshipStatus;

    private Set<RoleResDTO> roles = new HashSet<>();

    public UserResponseDTO(String fullName, String avatar) {
        this.fullName = fullName;
        this.avatar = avatar;
    }

    public void updateFullName() {
        this.fullName = this.firstName + " " + this.lastName;
    }

}
