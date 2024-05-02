package com.ll.topcastingbe.domain.order.exception;

public class AuthException extends BusinessException {
    public AuthException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
