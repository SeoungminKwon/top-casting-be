package com.ll.topcastingbe.domain.order.dto.order_item;

import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record FindOrderItemDto(String itemImagePath,
                               String itemName,
                               String itemOption,
                               Long itemQuantity,
                               Long totalPrice) {

    public static FindOrderItemDto of(final FindOrderItemResponse orderItemResponse) {
        final FindOrderItemDto findOrderItemDto = FindOrderItemDto.builder()
                .itemImagePath(orderItemResponse.itemImagePath())
                .itemName(orderItemResponse.itemName())
                .itemOption(orderItemResponse.itemOption())
                .itemQuantity(orderItemResponse.itemQuantity())
                .totalPrice(orderItemResponse.totalPrice())
                .build();
        return findOrderItemDto;
    }

    public static List<FindOrderItemDto> ofList(final List<FindOrderItemResponse> orderItems) {
        final List<FindOrderItemDto> findOrderItemDtos = orderItems.stream()
                .map(FindOrderItemDto::of)
                .toList();
        return findOrderItemDtos;
    }
}
