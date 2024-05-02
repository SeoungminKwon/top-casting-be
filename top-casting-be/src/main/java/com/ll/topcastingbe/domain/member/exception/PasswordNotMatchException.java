package com.ll.topcastingbe.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
        super(UserErrorMessage.PASSWORD_NOT_MATCH.getMessage());
    }
    public PasswordNotMatchException(String msg) {
        super(msg);
    }
}
