package com.ll.topcastingbe.domain.order.service.order;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_EXCHANGE_REQUESTED;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_REFUND_REQUESTED;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.RequestCancelOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.OrderStatus;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.service.order_item.OrderItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@EnableAsync
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    @Override
    @Transactional
    public AddOrderResponse addOrder(final AddOrderRequest addOrderRequest, final Member member) {
        final Orders order = addOrderRequest.toOrder(member);
        orderRepository.save(order);

        addOrderItem(order, addOrderRequest);
        final AddOrderResponse addOrderResponse = AddOrderResponse.of(order);
        return addOrderResponse;
    }

    //todo 나중에 리팩토링 해야함
    @Override
    public FindOrderResponse findOrder(final UUID orderId, final Member member) {
        final Orders order = findByOrderId(orderId);
        order.checkAuthorizedMember(member);

        List<FindOrderItemResponse> findOrderItemResponses = orderItemService.findAllByOrderId(orderId, member);
        final FindOrderResponse findOrderResponse = FindOrderResponse.of(order, findOrderItemResponses);

        return findOrderResponse;
    }

    @Override
    public List<FindOrderResponse> findOrderList(final Member member) {
        final List<Orders> orders = orderRepository.findAllByMember(member);
        List<FindOrderResponse> findOrderResponses = addOrderResponse(orders);
        return findOrderResponses;
    }

    @Override
    @Transactional
    public void modifyOrder(final UUID orderId, final ModifyOrderRequest modifyOrderRequest, final Member member) {
        final Orders order = findByOrderId(orderId);

        order.checkAuthorizedMember(member);

        order.modifyOrder(modifyOrderRequest);
    }


    //todo 주문 삭제 기능 보류
    @Override
    @Transactional
    public void removeOrder(final UUID orderId, final Member member) {
        final Orders order = findByOrderId(orderId);
        order.checkAuthorizedMember(member);
        orderItemService.removeAllByOrder(order);
        orderRepository.delete(order);
    }

    @Override
    public Orders findByOrderId(final UUID orderId) {
        final Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
        return order;
    }

    @Override
    public void checkAuthorizedMemberList(List<Orders> orders, Member member) {
        orders.stream()
                .forEach(order -> order.checkAuthorizedMember(member));
    }

    @Override
    @Transactional
    public void requestCancelOrder(final UUID orderId,
                                   final RequestCancelOrderRequest requestCancelOrderRequest,
                                   final Member member) {
        final OrderStatus orderStatus = OrderStatus.checkOrderStatus(requestCancelOrderRequest.orderStatus());
        if (orderStatus == ORDER_REFUND_REQUESTED ||
                orderStatus == ORDER_EXCHANGE_REQUESTED) {
            final Orders order = findByOrderId(orderId);
            order.checkAuthorizedMember(member);
            order.modifyOrderStatus(orderStatus);
        }
    }

    public List<FindOrderResponse> addOrderResponse(final List<Orders> orders) {
        List<FindOrderResponse> findOrderResponses = new ArrayList<>();

        for (Orders order : orders) {
            final List<FindOrderItemResponse> findOrderItemResponses =
                    orderItemService.findAllByOrderIdForAdmin(order.getId());
            final FindOrderResponse findOrderResponse = FindOrderResponse.of(order, findOrderItemResponses);
            findOrderResponses.add(findOrderResponse);
        }
        return findOrderResponses;
    }

    public Long getTotalItemPrice(final Orders order) {
        final List<OrderItem> orderItems = orderItemService.findOrderItems(order);
        Long totalItemPrice = orderItems.stream()
                .mapToLong(OrderItem::getTotalPrice)
                .sum();
        return totalItemPrice;
    }

    @Transactional
    @Async("threadPoolTaskExecutor")
    @Retryable
    public CompletableFuture<String> deductStockForOrder(final Orders order) {

        List<OrderItem> orderItems = orderItemService.findOrderItemsWithPessimisticWriteLock(order);
        for (OrderItem orderItem : orderItems) {
            long newStock = orderItem.getOption().getStock() - orderItem.getItemQuantity();
            if (newStock < 0) {
                log.info("{}", "error");
                throw new BusinessException(ErrorMessage.INVALID_INPUT_VALUE);
            }
            orderItem.getOption().deductionStock(orderItem.getItemQuantity());
        }
        return CompletableFuture.completedFuture(null);
    }

    private void checkTotalItemPrice(final Orders order) {
        Long totalItemPrice = getTotalItemPrice(order);
        if (!Objects.equals(totalItemPrice, order.getTotalItemPrice())) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT_VALUE);
        }
    }

    private void addOrderItem(final Orders order, final AddOrderRequest addOrderRequest) {
        addOrderRequest.addOrderItemRequest()
                .forEach(addOrderItemRequest -> {
                    orderItemService.addOrderItem(order, addOrderItemRequest);
                });
    }
}
