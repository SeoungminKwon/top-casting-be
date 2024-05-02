package com.ll.topcastingbe.domain.payment.controller;

import com.ll.topcastingbe.domain.payment.dto.AddTossPaymentDto;
import com.ll.topcastingbe.domain.payment.service.PaymentService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/toss/success")
    public ResponseEntity<AddTossPaymentDto> tossPaymentSuccess(
            @RequestParam(name = "paymentKey") final String paymentKey,
            @RequestParam(name = "orderId") final UUID orderId, @RequestParam(name = "amount") final Long amount)
            throws IOException, InterruptedException {
        AddTossPaymentDto addTossPaymentDto = AddTossPaymentDto.of(
                paymentService.addPayment(orderId, paymentKey, amount));
        return ResponseEntity.ok(addTossPaymentDto);
    }
}
