package com.ll.topcastingbe.domain.cart.dto;

import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.global.constant.ShippingConst;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class CartItemListResponseDto {
    private List<CartItemResponseDto> cartItems;
    private BigDecimal shippingFee;
    private BigDecimal freeShippingCond;

    public static CartItemListResponseDto toDto(List<CartItem> cartItems) {
        List<CartItemResponseDto> cartItemResponseDtos = cartItems.stream()
                .map(CartItemResponseDto::toDto).toList();

        return new CartItemListResponseDto(cartItemResponseDtos, ShippingConst.SHIPPING_FEE, ShippingConst.FREE_SHIPPING_COND);
    }
}
