package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderForAdminResponse;
import com.ll.topcastingbe.domain.order.dto.order_item.FindOrderItemDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record FindOrderForAdminDto(UUID orderId,
                                   String paymentKey,
                                   String customerName,
                                   String customerPhoneNumber,
                                   String customerAddress,
                                   String orderStatus,
                                   LocalDateTime orderCreatedDate,
                                   Long totalItemQuantity,
                                   Long totalItemPrice,
                                   List<FindOrderItemDto> findOrderItemDtos) {

    public static FindOrderForAdminDto of(final FindOrderForAdminResponse findOrderForAdminResponse) {
        final FindOrderForAdminDto findOrderDto = FindOrderForAdminDto.builder()
                .paymentKey(findOrderForAdminResponse.paymentKey())
                .orderId(findOrderForAdminResponse.orderId())
                .customerName(findOrderForAdminResponse.customerName())
                .customerPhoneNumber(findOrderForAdminResponse.customerPhoneNumber())
                .customerAddress(findOrderForAdminResponse.customerAddress())
                .orderStatus(findOrderForAdminResponse.orderStatus())
                .orderCreatedDate(findOrderForAdminResponse.orderCreatedDate())
                .totalItemQuantity(findOrderForAdminResponse.totalItemQuantity())
                .totalItemPrice(findOrderForAdminResponse.totalItemPrice())
                .findOrderItemDtos(FindOrderItemDto.ofList(findOrderForAdminResponse.findOrderItemResponses()))
                .build();
        return findOrderDto;
    }

    public static List<FindOrderForAdminDto> ofList(final List<FindOrderForAdminResponse> findOrderForAdminResponses) {
        final List<FindOrderForAdminDto> findOrderDtos = findOrderForAdminResponses.stream()
                .map(FindOrderForAdminDto::of)
                .toList();
        return findOrderDtos;
    }
}
