package com.ll.topcastingbe.domain.payment.service;

import com.ll.topcastingbe.domain.order.entity.OrderStatus;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.service.order.OrderService;
import com.ll.topcastingbe.domain.payment.dto.response.AddTossPaymentResponse;
import com.ll.topcastingbe.domain.payment.entity.Payment;
import com.ll.topcastingbe.domain.payment.repository.PaymentRepository;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final TossPaymentConfig tossPaymentConfig;

    //todo ErrorMessage추가
    //
    @Override
    @Transactional
    public AddTossPaymentResponse addPayment(final UUID orderId, final String paymentKey, final Long price) {
        final Orders order = orderService.findByOrderId(orderId);
        checkOrderPrice(order, price);
        order.modifyOrderStatus(OrderStatus.SHIPPING);

        final Payment payment = createPayment(order, price, paymentKey);
        paymentRepository.save(payment);

        requestPaymentAccept(paymentKey, price, orderId);

        final AddTossPaymentResponse tossPaymentResponse = AddTossPaymentResponse.of(payment);
        return tossPaymentResponse;
    }

    public Payment createPayment(final Orders order, final Long price, final String paymentKey) {
        final Payment payment = Payment.builder()
                .order(order)
                .price(price)
                .paymentKey(paymentKey)
                .build();
        return payment;
    }

    private void requestPaymentAccept(final String paymentKey, final Long price, final UUID orderId) {
        final URI apiUri = URI.create(tossPaymentConfig.getTossApiUri());
        final String requestBody = String.format(
                "{\"paymentKey\":\"%s\",\"amount\":%d,\"orderId\":\"%s\"}",
                paymentKey, price, orderId
        );
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(apiUri)
                .header("Authorization", tossPaymentConfig.getTestTossAuthorizationHeader())
                .header("Content-Type", tossPaymentConfig.getContentTypeHeader())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        sendTossPaymentApproveRequest(request);
    }

    private void sendTossPaymentApproveRequest(final HttpRequest request) {
        try {
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ioe) {
            throw new IllegalArgumentException("IoException");
        } catch (InterruptedException ie) {
            throw new IllegalArgumentException("InterruptedException ie");
        }
    }

    private void checkOrderPrice(final Orders order, final Long price) {
        if (!Objects.equals(order.getTotalItemPrice(), price)) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT_VALUE);
        }
    }
}
