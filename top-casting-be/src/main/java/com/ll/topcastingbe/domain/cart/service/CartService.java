package com.ll.topcastingbe.domain.cart.service;

import com.ll.topcastingbe.domain.cart.dto.CartItemListResponseDto;
import com.ll.topcastingbe.domain.cart.entity.Cart;
import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.domain.cart.exception.CartItemNotExistException;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.cart.repository.CartRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.exception.UserAndWriterNotMatchException;
import com.ll.topcastingbe.domain.member.exception.UserNotFoundException;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.exception.OptionNotFoundException;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OptionRepository optionRepository;

    //상품페이지에서 장바구니에 추가를 선택한 경우
    @Transactional
    public void addCartItem(Long memberId, Long optionId, int itemQuantity) {

        //장바구니가 존재하는지 확인 -> 없으면 생성
        Cart cart = cartRepository.findCartByMemberId(memberId).orElseGet(() -> createCart(memberId));

        CartItem cartItem = cartItemRepository.findByCartIdAndOptionId(cart.getId(), optionId);
        //카트안에 해당 상품이 없었다면 추가
        if (cartItem == null) {
            Option option = optionRepository.findById(optionId)
                    .orElseThrow(() -> new OptionNotFoundException());
            cartItemRepository.save(new CartItem(cart, option, itemQuantity));
        } else {
            cartItem.changeItemQuantity(itemQuantity);
        }
    }

    private Cart createCart(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException());
        log.info("장바구니를 생성합니다.");
        return cartRepository.save(new Cart(member));
    }

    //장바구니에 있는 상품을 증감하는 경우
    @Transactional
    public void modifyCartItem(Long memberId, Long cartItemId, int itemQuantity) {
        CartItem cartItem = getCartItemAndValidateMember(memberId, cartItemId);

        cartItem.changeItemQuantity(itemQuantity);
    }

    private CartItem getCartItemAndValidateMember(Long memberId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findByIdWithMember(cartItemId)
                .orElseThrow(() -> new CartItemNotExistException());

        if (!cartItem.isMatched(memberId)) {
            throw new UserAndWriterNotMatchException();
        }
        return cartItem;
    }

    public CartItemListResponseDto findCartItemList(Long memberId) {
        //장바구니가 존재하는지 확인 -> 없으면 생성
        Cart cart = cartRepository.findCartByMemberId(memberId).orElseGet(() -> createCart(memberId));
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        return CartItemListResponseDto.toDto(cartItems);
    }

    @Transactional
    public void removeCartItem(Long memberId, Long cartItemId) {
        CartItem cartItem = getCartItemAndValidateMember(memberId, cartItemId);

        cartItemRepository.delete(cartItem);
    }

}

