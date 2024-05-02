package com.ll.topcastingbe.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class AdditionalInfoRequestDto {

    @NotNull
    private String nickname;

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
