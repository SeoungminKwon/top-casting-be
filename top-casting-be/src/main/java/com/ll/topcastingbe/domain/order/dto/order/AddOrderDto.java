package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.AddOrderItemDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record AddOrderDto(@NotBlank(message = "주문자 이름은 반드시 입력해야합니다.") String customerName,
                          @NotBlank(message = "주문자 주소는 반드시 입력해야합니다.") String customerAddress,
                          @NotBlank(message = "주문자 전화번호는 반드시 입력해야합니다.") String customerPhoneNumber,
                          @NotNull(message = "아이템갯수는 반드시 입력해야합니다.") Long totalItemQuantity,
                          @NotNull(message = "가격은 반드시 입력해야합니다.") Long totalItemPrice,
                          @NotNull(message = "아이템은 반드시 입력되어야합니다.") List<AddOrderItemDto> addOrderItemDtos) {

    public AddOrderRequest toAddOrderRequest() {
        AddOrderRequest addOrderRequest = AddOrderRequest.builder()
                .customerName(customerName)
                .customerAddress(customerAddress)
                .customerPhoneNumber(customerPhoneNumber)
                .totalItemQuantity(totalItemQuantity)
                .totalItemPrice(totalItemPrice)
                .addOrderItemRequest(addOrderItemDtos.stream()
                        .map(AddOrderItemDto::toOrderItemDto)//todo toOrderItemDto변환 메서드 이름 변경 필요(로직생각해보기)
                        .toList())
                .build();

        return addOrderRequest;
    }
}
