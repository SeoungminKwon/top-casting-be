package com.ll.topcastingbe.domain.order.repository.order_item;

import com.ll.topcastingbe.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}