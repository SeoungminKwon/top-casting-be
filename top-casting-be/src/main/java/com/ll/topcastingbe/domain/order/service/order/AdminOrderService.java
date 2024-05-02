package com.ll.topcastingbe.domain.order.service.order;

import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderForAdminResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import java.util.List;
import java.util.UUID;


public interface AdminOrderService {
    List<FindOrderResponse> findOrderListForAdmin();

    List<FindOrderResponse> findAllCancelOrderRequestsForAdmin();

    FindOrderForAdminResponse findOrderForAdmin(final UUID orderId);

}
