package com.ll.topcastingbe.domain.order.exception;


public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
