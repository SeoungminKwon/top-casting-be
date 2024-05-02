package com.ll.topcastingbe.domain.payment.repository;

import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentKey(final String paymentKey);

    Optional<Payment> findByOrder(final Orders order);
}
