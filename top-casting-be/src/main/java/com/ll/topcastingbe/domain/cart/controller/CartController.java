package com.ll.topcastingbe.domain.cart.controller;

import com.ll.topcastingbe.domain.cart.dto.AddToCartRequestDto;
import com.ll.topcastingbe.domain.cart.dto.CartItemListResponseDto;
import com.ll.topcastingbe.domain.cart.dto.CartItemQuantityUpdateRequestDto;
import com.ll.topcastingbe.domain.cart.service.CartService;
import com.ll.topcastingbe.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> cartList(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.findCartItemList(principal.getMember().getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> cartItemAdd(@AuthenticationPrincipal PrincipalDetails principal,
                                         @RequestBody AddToCartRequestDto cartItemDto) {
        cartService.addCartItem(principal.getMember().getId(), cartItemDto.getOptionId(),
                cartItemDto.getItemQuantity());
        CartItemListResponseDto cartItemList = cartService.findCartItemList(principal.getMember().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemList);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{cartItemId}")
    public ResponseEntity<?> cartItemModify(@AuthenticationPrincipal PrincipalDetails principal,
                                            @PathVariable Long cartItemId,
                                            @RequestBody CartItemQuantityUpdateRequestDto itemQuantityDto) {
        cartService.modifyCartItem(principal.getMember().getId(), cartItemId, itemQuantityDto.getItemQuantity());
        CartItemListResponseDto cartItemList = cartService.findCartItemList(principal.getMember().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemList);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> cartItemDelete(@AuthenticationPrincipal PrincipalDetails principal,
                                            @PathVariable Long cartItemId) {
        cartService.removeCartItem(principal.getMember().getId(), cartItemId);
        return ResponseEntity.ok(null);
    }

}
