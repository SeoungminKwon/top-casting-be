package com.ll.topcastingbe.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate birthDate;

    @Embedded
    private Address address;

    private String phoneNumber;

    private String roles;

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void grantRole() {
        if (username.equals("admin")) {
            roles = "ROLE_ADMIN, ROLE_USER";
        } else {
            roles = "ROLE_USER";
        }
    }

    public void changeDetails(String nickname, String email, Address address, String phoneNumber) {
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void changeDetailsForSicailLogin(String nickname, Address address, String phoneNumber, LocalDate birthDate) {
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }


}
