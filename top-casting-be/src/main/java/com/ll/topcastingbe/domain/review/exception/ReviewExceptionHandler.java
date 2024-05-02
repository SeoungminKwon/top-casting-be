package com.ll.topcastingbe.domain.review.exception;

import com.ll.topcastingbe.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ReviewExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleCartItemNotExistExceptions(ReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body(ErrorResponseDto.builder()
                                     .code(HttpStatus.NOT_FOUND.toString())
                                     .message(e.getMessage())
                                     .build()
                       );

    }
}
