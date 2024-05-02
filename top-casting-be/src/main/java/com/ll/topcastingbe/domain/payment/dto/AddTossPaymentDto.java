package com.ll.topcastingbe.domain.payment.dto;

import com.ll.topcastingbe.domain.payment.dto.response.AddTossPaymentResponse;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AddTossPaymentDto(UUID orderId,
                                Long price,
                                String customerAddress) {

    public static AddTossPaymentDto of(final AddTossPaymentResponse addTossPaymentResponse) {
        final AddTossPaymentDto addTossPaymentDto = AddTossPaymentDto.builder()
                .orderId(addTossPaymentResponse.orderId())
                .price(addTossPaymentResponse.price())
                .customerAddress(addTossPaymentResponse.customerAddress())
                .build();

        return addTossPaymentDto;
    }
}
