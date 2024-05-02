package com.ll.topcastingbe.domain.cart.exception;

import com.ll.topcastingbe.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CartExceptionControllerAdvice {

    //카트에 해당 아이템을 찾을 수 없을때 발생하는 예외처리
    @ExceptionHandler(CartItemNotExistException.class)
    public ResponseEntity<ErrorResponseDto> handleCartItemNotExistExceptions(CartItemNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }
}
