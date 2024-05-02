package com.ll.topcastingbe.domain.member.controller;

import com.ll.topcastingbe.domain.member.dto.JoinRequestDto;
import com.ll.topcastingbe.domain.member.dto.MemberInfoResponseDto;
import com.ll.topcastingbe.domain.member.repository.RefreshTokenService;
import com.ll.topcastingbe.domain.member.service.MemberService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class MemberController {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinRequestDto joinRequestDto) {
        if (!joinRequestDto.getPassword().equals(joinRequestDto.getPasswordCheck())) {
            return ResponseEntity
                           .status(HttpStatus.BAD_REQUEST)
                           .body("패스워드를 다시 한번 확인해주세요");
        }
        if (memberService.checkNickname(joinRequestDto.getNickname())) {
            return ResponseEntity
                           .status(HttpStatus.CONFLICT)
                           .body("중복되는 닉네임이 존재합니다.");
        }
        memberService.join(joinRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public MemberInfoResponseDto info(Principal principal) {
        MemberInfoResponseDto memberInfo = memberService.findMemberInfo(principal.getName());
        return memberService.findMemberInfo(principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "RefreshToken") String refreshToken) {
        refreshTokenService.expireToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
