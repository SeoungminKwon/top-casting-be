package com.ll.topcastingbe.domain.order.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
