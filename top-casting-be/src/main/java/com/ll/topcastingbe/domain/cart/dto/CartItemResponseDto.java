package com.ll.topcastingbe.domain.cart.dto;

import java.math.BigDecimal;

import com.ll.topcastingbe.domain.cart.entity.CartItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponseDto {
	private Long cartItemId;
	private String itemImage;
	private String itemName;
	private String itemColor;
	private int itemQuantity;
	private BigDecimal itemPrice;

	public static CartItemResponseDto toDto(CartItem cartItem) {
		return CartItemResponseDto.builder()
			.cartItemId(cartItem.getId())
			.itemImage(cartItem.getOption().getItem().getImage().getPath())
			.itemName(cartItem.getOption().getItem().getItemName())
			.itemColor(cartItem.getOption().getColorName())
			.itemQuantity(cartItem.getItemQuantity())
			.itemPrice(cartItem.getOption().getItem().getItemPrice()).build();
	}
}
