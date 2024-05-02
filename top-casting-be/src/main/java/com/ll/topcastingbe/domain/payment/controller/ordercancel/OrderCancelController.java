package com.ll.topcastingbe.domain.payment.controller.ordercancel;


import com.ll.topcastingbe.domain.payment.service.ordercancel.OrderCancelService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderCancelController {
    private final OrderCancelService orderCancelService;

    @DeleteMapping("/api/v1/admin/refund")
    public ResponseEntity<Void> orderCancel(@RequestParam String paymentKey,
                                            @RequestParam String cancelReason)
            throws IOException, InterruptedException {
        orderCancelService.tossPaymentCancel(paymentKey, cancelReason);
        return ResponseEntity.ok().build();
    }
}
