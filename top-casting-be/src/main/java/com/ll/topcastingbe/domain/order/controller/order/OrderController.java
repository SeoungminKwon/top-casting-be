package com.ll.topcastingbe.domain.order.controller.order;


import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.AddOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.AddOrderResponseDto;
import com.ll.topcastingbe.domain.order.dto.order.FindOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.RequestCancelOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.request.RequestCancelOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.service.order.OrderService;
import com.ll.topcastingbe.global.security.auth.PrincipalDetails;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/orders")
    public ResponseEntity<AddOrderResponseDto> orderAdd(@Valid @RequestBody final AddOrderDto addOrderDto,
                                                        @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        final AddOrderResponseDto addOrderResponseDto =
                AddOrderResponseDto.of(orderService.addOrder(addOrderDto.toAddOrderRequest(), member));

        return ResponseEntity.created(URI.create(""))
                .header("Location", "")
                .body(addOrderResponseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<FindOrderDto> orderFind(@PathVariable("orderId") final UUID orderId,
                                                  @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        final FindOrderDto findOrderDto = FindOrderDto.of(orderService.findOrder(orderId, member));

        return ResponseEntity.ok(findOrderDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orders")
    public ResponseEntity<List<FindOrderDto>> orderFindAll(
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        List<FindOrderResponse> findOrderResponses = orderService.findOrderList(member);
        final List<FindOrderDto> findOrderDtos = FindOrderDto.ofList(findOrderResponses);
        return ResponseEntity.ok(findOrderDtos);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> orderRemove(@PathVariable("orderId") final UUID orderId,
                                            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        orderService.removeOrder(orderId, member);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/orders/{orderId}/refund")
    public ResponseEntity<Void> cancelOrderRequest(@RequestBody final RequestCancelOrderDto requestCancelOrderDto,
                                                   @PathVariable("orderId") final UUID orderId,
                                                   @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        RequestCancelOrderRequest requestCancelOrderRequest = requestCancelOrderDto.toRequestCancelOrderRequest();
        orderService.requestCancelOrder(orderId, requestCancelOrderRequest, member);
        return ResponseEntity.ok().build();
    }
}
