package com.bunary.vocab.dto.request;

import java.time.Instant;
import java.time.LocalDate;

import com.bunary.vocab.model.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private String password;
    private String avatar;
    private String address;
    private Instant dateOfBirth;
    private GenderEnum gender;

    public void updateFullName() {
        this.fullName = this.firstName + " " + this.lastName;
    }

}
