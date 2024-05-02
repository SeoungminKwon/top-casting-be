package com.ll.topcastingbe.domain.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartItemNotExistException extends RuntimeException {

	public CartItemNotExistException() {
		super(CartErrorMessage.CART_ITEM_NOT_EXIST.getMessage());
	}
	public CartItemNotExistException(String message) {
		super(message);
	}
}
