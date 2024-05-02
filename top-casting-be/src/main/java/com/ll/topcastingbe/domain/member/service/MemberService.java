package com.ll.topcastingbe.domain.member.service;

import com.ll.topcastingbe.domain.cart.entity.Cart;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.cart.repository.CartRepository;
import com.ll.topcastingbe.domain.member.dto.AdditionalInfoRequestDto;
import com.ll.topcastingbe.domain.member.dto.JoinRequestDto;
import com.ll.topcastingbe.domain.member.dto.MemberInfoResponseDto;
import com.ll.topcastingbe.domain.member.entity.Address;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.exception.PasswordNotMatchException;
import com.ll.topcastingbe.domain.member.exception.UserNotFoundException;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public void join(JoinRequestDto joinRequestDto) {
        Member member = Member.builder()
                                .username(joinRequestDto.getUsername())
                                .password(passwordEncoder.encode(joinRequestDto.getPassword()))
                                .nickname(joinRequestDto.getNickname())
                                .name(joinRequestDto.getName())
                                .email(joinRequestDto.getEmail())
                                .birthDate(joinRequestDto.getBirthDate())
                                .phoneNumber(joinRequestDto.getPhoneNumber())
                                .address(Address.builder()
                                                 .address1(joinRequestDto.getAddress1())
                                                 .address2(joinRequestDto.getAddress2())
                                                 .zipcode(joinRequestDto.getZipcode())
                                                 .build())
                                .build();
        member.grantRole();

        memberRepository.save(member);
    }

    public boolean checkNickname(String nickname) {
        Optional<Member> om = memberRepository.findByNickname(nickname);
        if (om.isEmpty()) {
            return false;
        }
        return true;
    }


    public Member findMember(String username) {
        return memberRepository.findByUsername(username);
    }


    public MemberInfoResponseDto findMemberInfo(String username) {
        return new MemberInfoResponseDto(memberRepository.findByUsername(username));
    }

    @Transactional
    public void modifyMember(Long memberId, String nickname, String password, String email, String address1,
                             String address2, String zipcode, String phoneNumber) {
        //패스워드가 실제 member 패스워드와 일치하는지 확인
        Member findMember = validateAndFindMember(memberId, password);

        Address address = Address.builder()
                                  .address1(address1)
                                  .address2(address2)
                                  .zipcode(zipcode).build();

        findMember.changeDetails(nickname, email, address, phoneNumber);
    }

    private Member validateAndFindMember(Long memberId, String password) {
        Member member = memberRepository.findById(memberId)
                                .orElseThrow(() -> new UserNotFoundException());

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new PasswordNotMatchException();
        }
        return member;
    }

    @Transactional
    public void removeMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                                .orElseThrow(() -> new UserNotFoundException());
        //장바구니가 있다면 장바구니 먼저 삭제
        Cart cart = cartRepository.findCartByMemberId(member.getId()).orElse(null);
        if (cart != null) {
            cartItemRepository.deleteCartItemByCartId(cart.getId());
            cartRepository.delete(cart);
        }
        memberRepository.delete(member);
    }

    @Transactional
    public void saveAdditionalInfo(String name, AdditionalInfoRequestDto requestDto) {
        Member findMember = memberRepository.findByUsername(name);
        if (findMember == null) {
            return;
        }
        Address address = Address.builder()
                                  .address1(requestDto.getAddress1())
                                  .address2(requestDto.getAddress2())
                                  .zipcode(requestDto.getZipcode())
                                  .build();
        findMember.changeDetailsForSicailLogin(requestDto.getNickname(), address, requestDto.getPhoneNumber(),
                requestDto.getBirthDate());
        findMember.grantRole();
    }
}
