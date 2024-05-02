package com.ll.topcastingbe.domain.order.repository.order;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.entity.OrderStatus;
import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Orders, UUID> {

    @EntityGraph(attributePaths = {"member"})
    Optional<Orders> findById(final UUID orderId);

    @EntityGraph(attributePaths = {"member"})
    List<Orders> findAllByMember(final Member member);

    @Query("SELECT o FROM Orders o WHERE o.orderStatus = :orderStatusRefund OR o.orderStatus = :orderStatusExchange")
    List<Orders> findByOrderStatusRefundOrOrderStatusExchange(final OrderStatus orderStatusRefund,
                                                              final OrderStatus orderStatusExchange);
}
