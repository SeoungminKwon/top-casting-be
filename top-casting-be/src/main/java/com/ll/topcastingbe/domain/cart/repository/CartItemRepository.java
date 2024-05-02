package com.ll.topcastingbe.domain.cart.repository;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ll.topcastingbe.domain.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("select ci from CartItem ci join fetch ci.option op join fetch op.item it join fetch it.image i where ci.cart.id = :cartId")
	List<CartItem> findByCartId(@Param("cartId") Long cartId);

	//장바구니에 해당 아이템+옵션이 있는지 확인하는 용도
	@Query("select ci from CartItem ci where ci.cart.id =:cartId and ci.option.id = :optionId ")
	CartItem findByCartIdAndOptionId(@Param("cartId") Long cartId, @Param("optionId") Long optionId);

	@Query("select ci from CartItem ci join fetch ci.cart c where ci.id = :cartItemId")
	Optional<CartItem> findByIdWithMember(@Param("cartItemId")Long cartItemId);

	@Modifying(clearAutomatically = true) //벌크연산수행 & 수행후 영속성 컨텍스트 초기화
	@Query("delete from CartItem ci where ci.cart.id = :cartId")
	int deleteCartItemByCartId(@Param("cartId") Long cartId);
}
