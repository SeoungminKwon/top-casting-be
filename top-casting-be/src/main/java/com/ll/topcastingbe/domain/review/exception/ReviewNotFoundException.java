package com.ll.topcastingbe.domain.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {
        super(ReviewErrorMessage.NOT_FOUND.getMessage());
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
