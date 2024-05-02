package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.request.RequestCancelOrderRequest;
import jakarta.validation.constraints.NotBlank;

public record RequestCancelOrderDto(@NotBlank(message = "주문상태입력은 필수입니다.") String orderStatus) {
    public RequestCancelOrderRequest toRequestCancelOrderRequest() {
        final RequestCancelOrderRequest requestCancelOrderRequest = RequestCancelOrderRequest.builder()
                .orderStatus(orderStatus)
                .build();
        return requestCancelOrderRequest;
    }
}
