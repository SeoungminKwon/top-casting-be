package com.ll.topcastingbe.domain.order.dto.order.response;

import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AddOrderResponse(UUID orderId) {

    public static AddOrderResponse of(final Orders order) {
        AddOrderResponse addOrderResponse = AddOrderResponse.builder()
                .orderId(order.getId())
                .build();

        return addOrderResponse;
    }
}
