package com.ll.topcastingbe.domain.member.repository;

import com.ll.topcastingbe.domain.member.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void expireToken(String token) {
        RefreshToken findToken = refreshTokenRepository.findByToken(token);
        if(findToken == null){
            return;
        }
        findToken.uncheck();
    }
}
