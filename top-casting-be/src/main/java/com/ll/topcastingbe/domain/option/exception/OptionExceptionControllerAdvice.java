package com.ll.topcastingbe.domain.option.exception;

import com.ll.topcastingbe.domain.cart.exception.CartItemNotExistException;
import com.ll.topcastingbe.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OptionExceptionControllerAdvice {

    //해당 옵션이 존재하지 않을 때 발생하는 예외처리
    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOptionNotFoundExceptions(OptionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }

}
