package com.ll.topcastingbe.domain.member.exception;

import com.ll.topcastingbe.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserExceptionControllerAdvice {

    //요청 사용자와 작성자가 다른 경우 예외처리
    @ExceptionHandler(UserAndWriterNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAndWriterNotMatchExceptions(UserAndWriterNotMatchException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.FORBIDDEN.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }

    //해당 사용자를 찾지 못한 경우 예외처리
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundExceptions(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(PasswordAndPasswordCheckNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordAndPasswordCheckNotMatchExceptions(
            PasswordAndPasswordCheckNotMatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.BAD_REQUEST.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordNotMatchExceptions(PasswordNotMatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .code(HttpStatus.BAD_REQUEST.toString())
                        .message(ex.getMessage())
                        .build()
                );
    }
}
