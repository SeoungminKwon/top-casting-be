package com.ll.topcastingbe.domain.order.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {
    ENTITY_NOT_FOUND("Entity를 찾을 수 없습니다."),
    UNAUTHORIZED_USER("권한이 없는 사용자입니다."),
    INVALID_INPUT_VALUE("허용되지 않는 입력값입니다."),
    INTERNAL_SERVER_ERROR("서버에 문제가 생겼습니다.");

    private final String message;
}
