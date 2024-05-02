package com.ll.topcastingbe.domain.cart.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CartErrorMessage {

    CART_ITEM_NOT_EXIST("장바구니에 해당 상품이 없습니다.");

    private final String message;
}
