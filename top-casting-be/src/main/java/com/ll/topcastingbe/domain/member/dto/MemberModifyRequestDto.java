package com.ll.topcastingbe.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class MemberModifyRequestDto {

    private String nickname;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordCheck;
    private String email;
    private String address1;
    private String address2;
    private String zipcode;
    private String phoneNumber;

    public boolean checkPasswordMatch() {
        return password.equals(passwordCheck);
    }
}
