package com.ll.topcastingbe.domain.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ll.topcastingbe.domain.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

	@Query("select c from Cart c where c.member.id = :memberId")
	Optional<Cart> findCartByMemberId(@Param("memberId") Long memberId);
}
