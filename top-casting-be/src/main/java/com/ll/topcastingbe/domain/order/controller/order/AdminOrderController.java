package com.ll.topcastingbe.domain.order.controller.order;

import com.ll.topcastingbe.domain.order.dto.order.FindOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.FindOrderForAdminDto;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderForAdminResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.service.order.AdminOrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/refund")
    public ResponseEntity<List<FindOrderDto>> cancelOrderRequestsFindAllForAdmin() {
        final List<FindOrderResponse> findOrderResponses = adminOrderService.findAllCancelOrderRequestsForAdmin();
        final List<FindOrderDto> findOrderDtos = FindOrderDto.ofList(findOrderResponses);
        return ResponseEntity.ok().body(findOrderDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/orders")
    public ResponseEntity<List<FindOrderDto>> orderFindAllForAdmin() {
        final List<FindOrderDto> findOrderDtos = FindOrderDto.ofList(adminOrderService.findOrderListForAdmin());
        return ResponseEntity.ok(findOrderDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/orders/{orderId}")
    public ResponseEntity<FindOrderForAdminDto> orderFindForAdmin(@PathVariable("orderId") final UUID orderId) {
        final FindOrderForAdminResponse findOrderForAdminResponse = adminOrderService.findOrderForAdmin(orderId);
        final FindOrderForAdminDto findOrderForAdminDto = FindOrderForAdminDto.of(findOrderForAdminResponse);
        return ResponseEntity.ok(findOrderForAdminDto);
    }
}
