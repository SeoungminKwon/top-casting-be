package com.ll.topcastingbe.domain.payment.entity;

import java.util.Arrays;
import lombok.Getter;


@Getter
public enum PayType {
    CARD, CASH, POINT;

    public static PayType checkPayType(final String type) {
        return Arrays.stream(PayType.values())
                .filter(payType -> payType.name().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("err"));
    }
}
