package com.ll.topcastingbe.domain.item.exception;

import com.ll.topcastingbe.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ItemExceptionControllerAdvice {

    //해당 아이템이 존재하지 않을때, 예외처리
    @ExceptionHandler(ItemNotExistException.class)
    public ResponseEntity<ErrorResponseDto> handleItemNotExistExceptions(ItemNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }
}
