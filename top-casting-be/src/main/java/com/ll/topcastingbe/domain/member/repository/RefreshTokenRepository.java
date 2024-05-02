package com.ll.topcastingbe.domain.member.repository;

import com.ll.topcastingbe.domain.member.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByTokenAndCheckFlag(String token, boolean checkFlag);

    RefreshToken findByToken(String token);
}
