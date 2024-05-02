package com.ll.topcastingbe.domain.order.service.order_item;

import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.request.ModifyOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {
    private final OptionRepository optionRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    //todo save가 두 번 호출되므로 정상적인지는 잘 모르겠음
    public void addOrderItem(Orders order, AddOrderItemRequest addOrderItemRequest) {
        final CartItem cartItem = cartItemRepository.findById(addOrderItemRequest.cartItemId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));

        final OrderItem orderItem = OrderItem.builder()
                .order(order)
                .option(cartItem.getOption())
                .itemQuantity(addOrderItemRequest.itemQuantity())
                .totalPrice(getTotalPrice(cartItem.getOption(), addOrderItemRequest)).build();

        orderItemRepository.save(orderItem);
    }

    private Long getTotalPrice(final Option option, final AddOrderItemRequest addOrderItemRequest) {
        //todo 코드 더러움 수정 필요
        final Long totalPrice =
                option.getItem().getItemPrice().longValue() *
                        addOrderItemRequest.itemQuantity();

        return totalPrice;
    }

    @Override
    public List<FindOrderItemResponse> findAllByOrderId(final UUID orderId, final Member member) {
        final Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));

        List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
        List<FindOrderItemResponse> orderItemResponses = FindOrderItemResponse.ofList(orderItems);

        return orderItemResponses;
    }

    @Override
    public List<FindOrderItemResponse> findAllByOrderIdForAdmin(final UUID orderId) {
        final Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));

        List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
        List<FindOrderItemResponse> orderItemResponses = FindOrderItemResponse.ofList(orderItems);

        return orderItemResponses;
    }

    public List<OrderItem> findOrderItems(final Orders order) {
        final List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
        return orderItems;
    }

    public List<OrderItem> findOrderItemsWithPessimisticWriteLock(final Orders order) {
        final List<OrderItem> orderItems = orderItemRepository.findAllByOrderWithPessimisticWriteLock(order);
        return orderItems;
    }

    @Override
    @Transactional
    public void updateOrderItem(Long orderItemId, ModifyOrderItemRequest modifyOrderItemRequest, Member member) {

    }

    @Override
    @Transactional
    public void removeAllByOrder(final Orders order) {
        orderItemRepository.removeAllByOrder(order);
    }
}
