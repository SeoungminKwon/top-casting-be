package com.ll.topcastingbe.domain.order.exception;

import com.ll.topcastingbe.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponseDto> authException(final AuthException e) {
        log.error(e.getMessage(), e);
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(HttpStatus.FORBIDDEN.toString())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorResponseDto);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> businessException(BusinessException e) {
        log.error(e.getMessage(), e);
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(HttpStatus.FORBIDDEN.toString())
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exception(Exception e) {
        log.error(e.getMessage(), e);
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(HttpStatus.FORBIDDEN.toString())
                .message(e.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(errorResponseDto);
    }

}
