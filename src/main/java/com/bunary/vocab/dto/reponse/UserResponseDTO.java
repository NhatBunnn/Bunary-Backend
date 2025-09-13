package com.bunary.vocab.dto.reponse;

import java.time.LocalDate;
import java.util.UUID;

import com.bunary.vocab.model.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private String avatar;
    private String address;
    private LocalDate dateOfBirth;
    private GenderEnum gender;

    public UserResponseDTO(String fullName, String avatar) {
        this.fullName = fullName;
        this.avatar = avatar;
    }

    public void updateFullName() {
        this.fullName = this.firstName + " " + this.lastName;
    }

}
