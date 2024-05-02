package com.ll.topcastingbe.domain.category.exception;

import com.ll.topcastingbe.domain.item.exception.ItemNotExistException;
import com.ll.topcastingbe.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CategoryExceptionControllerAdvice {

    //해당 카테고리가 존재하지 않을때, 예외처리
    @ExceptionHandler(CategoryNotExistException.class)
    public ResponseEntity<ErrorResponseDto> handleItemNotExistExceptions(CategoryNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }
}
