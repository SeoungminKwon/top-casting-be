package com.ll.topcastingbe.domain.order.service.order;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_EXCHANGE_REQUESTED;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_REFUND_REQUESTED;

import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderForAdminResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.service.order_item.OrderItemService;
import com.ll.topcastingbe.domain.payment.entity.Payment;
import com.ll.topcastingbe.domain.payment.repository.PaymentRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;

    @Override
    public List<FindOrderResponse> findOrderListForAdmin() {
        final List<Orders> orders = orderRepository.findAll();
        List<FindOrderResponse> findOrderResponses = orderService.addOrderResponse(orders);
        return findOrderResponses;
    }

    @Override
    public List<FindOrderResponse> findAllCancelOrderRequestsForAdmin() {
        final List<Orders> orders = orderRepository.findByOrderStatusRefundOrOrderStatusExchange(ORDER_REFUND_REQUESTED,
                ORDER_EXCHANGE_REQUESTED);
        List<FindOrderResponse> findOrderResponses = orderService.addOrderResponse(orders);
        return findOrderResponses;
    }


    @Override
    public FindOrderForAdminResponse findOrderForAdmin(final UUID orderId) {
        final Orders order = orderService.findByOrderId(orderId);
        final Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
        final List<FindOrderItemResponse> findOrderItemResponses =
                orderItemService.findAllByOrderIdForAdmin(order.getId());
        final FindOrderForAdminResponse findOrderForAdminResponse =
                FindOrderForAdminResponse.of(order, findOrderItemResponses, payment.getPaymentKey());
        return findOrderForAdminResponse;
    }

}
