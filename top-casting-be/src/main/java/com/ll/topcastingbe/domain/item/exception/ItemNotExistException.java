package com.ll.topcastingbe.domain.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotExistException extends RuntimeException {

	public ItemNotExistException() {
		super(ItemErrorMessage.ITEM_NOT_EXIST.getMessage());
	}
	public ItemNotExistException(String message) {
		super(message);
	}
}
