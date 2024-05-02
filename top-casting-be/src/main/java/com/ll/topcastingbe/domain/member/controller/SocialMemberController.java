package com.ll.topcastingbe.domain.member.controller;

import com.ll.topcastingbe.domain.member.dto.AdditionalInfoRequestDto;
import com.ll.topcastingbe.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SocialMemberController {
    private final MemberService memberService;
    @Value("${custom.site.front_url}")
    private String frontUrl;

    @GetMapping("/socialLogin/{providerTypeCode}")
    public String socialLogin(String redirectUrl, @PathVariable String providerTypeCode) {
        if (redirectUrl.startsWith(frontUrl)) {
            Cookie cookie = new Cookie("redirectUrlAfterSocialLogin", redirectUrl);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 10);
        }

        return "redirect:/oauth2/authorization/" + providerTypeCode;
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @PostMapping("/api/v1/auth/socialLogin/additionalInfo")
    public ResponseEntity<?> additionalInfo(@RequestBody @Valid AdditionalInfoRequestDto requestDto,
                                            Principal principal) {
        memberService.saveAdditionalInfo(principal.getName(), requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
