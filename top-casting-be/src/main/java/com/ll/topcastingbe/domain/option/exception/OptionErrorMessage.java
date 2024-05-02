package com.ll.topcastingbe.domain.option.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OptionErrorMessage {

    OPTION_NOT_FOUND("해당 옵션의 상품을 찾을 수 없습니다.");

    private final String message;
}
