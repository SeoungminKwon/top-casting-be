package com.ll.topcastingbe.domain.category.exception;

import com.ll.topcastingbe.domain.item.exception.ItemErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotExistException extends RuntimeException {

    public CategoryNotExistException(String message) {
        super(message);
    }

    public CategoryNotExistException(CategoryErrorMessage categoryErrorMessage) {
        super(categoryErrorMessage.getMessage());
    }
}
