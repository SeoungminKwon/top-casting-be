package com.ll.topcastingbe.domain.payment.repository.ordercancel;

import com.ll.topcastingbe.domain.payment.entity.OrderCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelRepository extends JpaRepository<OrderCancel, Long> {
}
