package com.ll.topcastingbe.domain.category.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CategoryErrorMessage {

    CATEGORY_NOT_EXIST("해당 카테고리가 존재하지 않습니다.");

    private final String message;
}
