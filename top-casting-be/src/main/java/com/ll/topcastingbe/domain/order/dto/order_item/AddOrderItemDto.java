package com.ll.topcastingbe.domain.order.dto.order_item;

import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddOrderItemDto(@NotNull(message = "cartItem의 값을 필수입니다.") Long cartItemId,
                              @Min(value = 1, message = "상품의 갯수는 1이상이어야 합니다.")
                              @NotNull(message = "상품의 갯수는 필수입니다.") Long itemQuantity) {

    public AddOrderItemRequest toOrderItemDto() {
        final AddOrderItemRequest addOrderItemRequest = AddOrderItemRequest.builder()
                .cartItemId(cartItemId)
                .itemQuantity(itemQuantity)
                .build();

        return addOrderItemRequest;
    }
}
