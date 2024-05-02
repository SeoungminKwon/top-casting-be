package com.ll.topcastingbe.domain.order.service.order;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.RequestCancelOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    AddOrderResponse addOrder(final AddOrderRequest addOrderRequest, final Member member);

    FindOrderResponse findOrder(final UUID orderId, final Member member);

    List<FindOrderResponse> findOrderList(final Member member);

    void modifyOrder(final UUID orderId, final ModifyOrderRequest modifyOrderRequest, final Member member);

    void removeOrder(final UUID orderId, final Member member);

    Orders findByOrderId(final UUID orderId);

    void checkAuthorizedMemberList(final List<Orders> orders, final Member member);

    void requestCancelOrder(final UUID orderId, final RequestCancelOrderRequest requestCancelOrderRequest,
                            final Member member);

    List<FindOrderResponse> addOrderResponse(final List<Orders> orders);

    Long getTotalItemPrice(final Orders order);

    CompletableFuture<String> deductStockForOrder(final Orders order);
}
