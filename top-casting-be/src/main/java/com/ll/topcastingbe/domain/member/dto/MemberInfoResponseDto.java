package com.ll.topcastingbe.domain.member.dto;

import com.ll.topcastingbe.domain.member.entity.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MemberInfoResponseDto {
    private Long id;
    private String username;
    private List<String> authorities;

    public MemberInfoResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.authorities = member.getRoleList();
    }
}
