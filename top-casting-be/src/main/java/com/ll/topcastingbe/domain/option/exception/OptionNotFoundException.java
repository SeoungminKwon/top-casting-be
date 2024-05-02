package com.ll.topcastingbe.domain.option.exception;

import com.ll.topcastingbe.domain.member.exception.UserErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OptionNotFoundException  extends RuntimeException {

    public OptionNotFoundException() {
        super(OptionErrorMessage.OPTION_NOT_FOUND.getMessage());
    }
    public OptionNotFoundException(String message) {
        super(message);
    }
}
