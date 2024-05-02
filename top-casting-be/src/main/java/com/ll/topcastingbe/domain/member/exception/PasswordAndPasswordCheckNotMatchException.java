package com.ll.topcastingbe.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordAndPasswordCheckNotMatchException extends RuntimeException {

    public PasswordAndPasswordCheckNotMatchException() {
        super(UserErrorMessage.PASSWORD_AND_PASSWORD_CHECK_NOT_MATCH.getMessage());
    }
    public PasswordAndPasswordCheckNotMatchException(String msg) {
        super(msg);
    }
}
