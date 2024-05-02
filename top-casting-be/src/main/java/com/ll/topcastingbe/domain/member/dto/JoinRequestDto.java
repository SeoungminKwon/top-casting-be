package com.ll.topcastingbe.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JoinRequestDto {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String passwordCheck;

    @NotNull
    private String nickname;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String address1;

    @NotNull
    private String address2;

    @NotNull
    private String zipcode;

    @NotNull
    private String phoneNumber;

}
