package com.ll.topcastingbe.domain.member.repository;

import com.ll.topcastingbe.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByNickname(String nickname);

    public Member findByUsername(String username);
}
