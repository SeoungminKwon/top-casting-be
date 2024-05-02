package com.ll.topcastingbe.domain.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateReviewException extends RuntimeException {

    public DuplicateReviewException() {
        super(ReviewErrorMessage.DUPLICATE_REVIEW.getMessage());
    }

    public DuplicateReviewException(String message) {
        super(message);
    }
}
