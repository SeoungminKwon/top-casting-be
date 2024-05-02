package com.ll.topcastingbe.domain.item.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ItemErrorMessage {

    ITEM_NOT_EXIST("해당 상품이 존재하지 않습니다.");

    private final String message;
}
