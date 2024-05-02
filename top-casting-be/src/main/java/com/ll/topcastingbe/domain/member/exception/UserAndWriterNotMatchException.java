package com.ll.topcastingbe.domain.member.exception;

import com.ll.topcastingbe.domain.cart.exception.CartErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAndWriterNotMatchException extends RuntimeException {

    public UserAndWriterNotMatchException() {
        super(UserErrorMessage.USER_AND_WRITER_NOT_MATCH.getMessage());
    }
    public UserAndWriterNotMatchException(String msg) {
        super(msg);
    }
}
