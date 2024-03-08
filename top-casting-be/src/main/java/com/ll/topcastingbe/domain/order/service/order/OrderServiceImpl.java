package com.ll.topcastingbe.domain.order.service.order;

import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetInitRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetItemInitRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.OrderSheetInitResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.OrderSheetItemInitResponse;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.service.order_item.OrderItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public AddOrderResponse addOrder(final AddOrderRequest addOrderRequest, final Member member) {
        final Orders order = addOrderRequest.toOrder(member);
        orderRepository.save(order);

        addOrderItem(order, addOrderRequest);

        final AddOrderResponse addOrderResponse = AddOrderResponse.of(order);
        return addOrderResponse;
    }

    @Override
    public FindOrderResponse findOrder(final UUID orderId, final Member member) {
        final Orders order = findByOrderId(orderId);
        order.checkAuthorizedMember(member);

        final FindOrderResponse findOrderResponse = FindOrderResponse.of(order);
        return findOrderResponse;
    }

    @Override
    public List<FindOrderResponse> findOrderList(final Member member) {
        final List<Orders> orders = orderRepository.findAllByMember(member);
        final List<FindOrderResponse> findOrderResponseList = FindOrderResponse.ofList(orders);

        return findOrderResponseList;
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


    //주문 시트 초기화시 필요한 정보 생성
    @Override
    public OrderSheetInitResponse initOrderSheet(final OrderSheetInitRequest orderSheetInitRequest,
                                                 final Member member) {

        List<OrderSheetItemInitResponse> orderSheetItemInitResponses = createOrderSheetItemResponses(
                orderSheetInitRequest);

        final OrderSheetInitResponse orderSheetInitResponse = OrderSheetInitResponse.builder()
                .memberName(member.getName())
                .memberAddress(member.getAddress().getAddress())
                .phoneNumber(member.getPhoneNumber())
                .shippingFee(orderSheetInitRequest.shippingFee())
                .orderSheetItemInitResponses(orderSheetItemInitResponses)
                .build();

        return orderSheetInitResponse;
    }

    ////itemQuantity를 검증 후 List<OrderSheetItemInitResponse>를 만듬
    private List<OrderSheetItemInitResponse> createOrderSheetItemResponses(
            final OrderSheetInitRequest orderSheetInitRequest) {

        List<OrderSheetItemInitResponse> orderSheetItemInitResponses = new ArrayList<>();

        for (OrderSheetItemInitRequest orderSheetItemInitRequest : orderSheetInitRequest.orderSheetItemInitRequests()) {
            final CartItem cartItem = cartItemRepository.findById(orderSheetItemInitRequest.cartItemId())
                    .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
            //ItemQuantity검증 메서드
            matchItemQuantity(orderSheetItemInitRequest, cartItem);

            final OrderSheetItemInitResponse orderSheetItemInitResponse = OrderSheetItemInitResponse.builder()
                    .optionId(cartItem.getOption().getId())
                    .itemQuantity(Long.valueOf(cartItem.getItemQuantity()))
                    .build();
            orderSheetItemInitResponses.add(orderSheetItemInitResponse);
        }
        return orderSheetItemInitResponses;
    }

    //갯수 검증 로직
    private void matchItemQuantity(final OrderSheetItemInitRequest orderSheetItemInitRequest, final CartItem cartItem) {
        if (cartItem.getItemQuantity() != orderSheetItemInitRequest.itemQuantity()) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT_VALUE);
        }
    }

    private void addOrderItem(final Orders order, final AddOrderRequest addOrderRequest) {
        addOrderRequest.addOrderItemRequest().stream()
                .forEach(addOrderItemRequest -> {
                    orderItemService.addOrderItem(order, addOrderItemRequest);
                });
    }
}