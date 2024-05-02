package com.ll.topcastingbe.domain.cart.entity;

import com.ll.topcastingbe.domain.option.entity.Option;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_id")
	private Option option;

	private int itemQuantity;

	public CartItem(Cart cart, Option option, int itemQuantity) {
		this.cart = cart;
		this.option = option;
		this.itemQuantity = itemQuantity;
	}

	public void changeItemQuantity(int quantity) {
		itemQuantity = quantity;
	}

	public boolean isMatched(Long memberId){
		return this.getCart().getMember().getId().equals(memberId);
	}
}
