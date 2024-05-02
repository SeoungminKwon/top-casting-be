package com.ll.topcastingbe.domain.payment.dto.response;

import com.ll.topcastingbe.domain.payment.entity.Payment;
import java.util.UUID;
import lombok.Builder;


@Builder
public record AddTossPaymentResponse(UUID orderId,
                                     Long price,
                                     String customerAddress) {

    public static AddTossPaymentResponse of(final Payment payment) {
        final AddTossPaymentResponse addTossPaymentResponse = AddTossPaymentResponse.builder()
                .orderId(payment.getOrderId())
                .price(payment.getPrice())
                .customerAddress(payment.getCustomerAddress())
                .build();

        return addTossPaymentResponse;
    }
}
