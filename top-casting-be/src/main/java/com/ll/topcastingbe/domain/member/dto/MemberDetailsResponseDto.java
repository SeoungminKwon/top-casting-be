package com.ll.topcastingbe.domain.member.dto;

import com.ll.topcastingbe.domain.member.entity.Member;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDetailsResponseDto {
    private String username;
    private String nickname;
    private String name;
    private String email;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String zipcode;

    public static MemberDetailsResponseDto toDto(Member member) {
        return MemberDetailsResponseDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
                .birthDate(member.getBirthDate())
                .phoneNumber(member.getPhoneNumber())
                .address1(member.getAddress().getAddress1())
                .address2(member.getAddress().getAddress2())
                .zipcode(member.getAddress().getZipcode())
                .build();
    }
}
