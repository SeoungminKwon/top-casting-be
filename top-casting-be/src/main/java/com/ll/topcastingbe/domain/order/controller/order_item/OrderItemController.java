package com.ll.topcastingbe.domain.order.controller.order_item;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.member.service.MemberService;
import com.ll.topcastingbe.domain.order.dto.order_item.FindOrderItemDto;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.service.order_item.OrderItemService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderItemController {
    private final MemberService memberService;
    private final OrderItemService orderItemService;
    private final MemberRepository memberRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/order-item/{orderId}")
    public ResponseEntity<List<FindOrderItemDto>> orderItemFindAll(@PathVariable("orderId") final UUID orderId,
                                                                   @AuthenticationPrincipal final UserDetails userDetails) {

        final Member member = memberRepository.findById(1L).get();
        final List<FindOrderItemResponse> findOrderItemResponse = orderItemService.findAllByOrderId(orderId, member);
        final List<FindOrderItemDto> findOrderItemDto = FindOrderItemDto.ofList(findOrderItemResponse);

        return ResponseEntity.ok(findOrderItemDto);
    }
}
