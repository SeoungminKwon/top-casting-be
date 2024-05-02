package com.ll.topcastingbe.domain.cart.dto;

import lombok.Data;

@Data
public class AddToCartRequestDto {
    private Long optionId;
    private int itemQuantity;
}
