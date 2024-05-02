package com.ll.topcastingbe.domain.order.entity;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum OrderStatus {
    WAITING, SHIPPING, DELIVERED, EXCHANGE, REFUND, ORDER_REFUND_REQUESTED,
    ORDER_EXCHANGE_REQUESTED;

    public static OrderStatus checkOrderStatus(final String status) {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
