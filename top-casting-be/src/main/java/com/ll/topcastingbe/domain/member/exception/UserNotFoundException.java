package com.ll.topcastingbe.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super(UserErrorMessage.USER_NOT_FOUND.getMessage());
	}
	public UserNotFoundException(String message) {
		super(message);
	}
}
