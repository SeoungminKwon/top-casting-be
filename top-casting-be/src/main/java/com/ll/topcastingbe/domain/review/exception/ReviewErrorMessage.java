package com.ll.topcastingbe.domain.review.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReviewErrorMessage {
    NOT_FOUND("해당 리뷰를 찾을 수 없습니다."),
    DUPLICATE_REVIEW("중복되는 리뷰가 존재합니다.");

    private String message;
}
