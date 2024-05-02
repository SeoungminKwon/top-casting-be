package com.ll.topcastingbe.domain.order.dto.order.request;

import lombok.Builder;

@Builder
public record RequestCancelOrderRequest(String orderStatus) {
}
