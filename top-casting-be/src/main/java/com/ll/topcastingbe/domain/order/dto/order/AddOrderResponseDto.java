package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AddOrderResponseDto(UUID orderId) {

    public static AddOrderResponseDto of(final AddOrderResponse addOrderResponse) {
        final AddOrderResponseDto addOrderResponseDto = AddOrderResponseDto.builder()
                .orderId(addOrderResponse.orderId())
                .build();

        return addOrderResponseDto;
    }
}
