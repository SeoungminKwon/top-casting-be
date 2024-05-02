package com.ll.topcastingbe.global.security.auth;

import com.ll.topcastingbe.domain.member.dto.oauth2.FaceBookResponse;
import com.ll.topcastingbe.domain.member.dto.oauth2.GoogleResponse;
import com.ll.topcastingbe.domain.member.dto.oauth2.KakaoResponse;
import com.ll.topcastingbe.domain.member.dto.oauth2.NaverResponse;
import com.ll.topcastingbe.domain.member.dto.oauth2.OAuth2Response;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else if(registrationId.equals("facebook")){

            oAuth2Response = new FaceBookResponse(oAuth2User.getAttributes());

        } else if(registrationId.equals("kakao")){

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        }

        else {

            return null;
        }

        String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();

        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            member = Member.builder()
                             .username(username)
                             .password(new BCryptPasswordEncoder().encode(
                                     username)) //소셜 로그인 멤버는 ID, PW값으로 로그인하지 않아서 이렇게 설정
                             .name(oAuth2Response.getName())
                             .nickname(oAuth2Response.getName())
                             .email(oAuth2Response.getEmail())
                             .roles("ROLE_SOCIALUSER")
                             .build();

            memberRepository.save(member);
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
