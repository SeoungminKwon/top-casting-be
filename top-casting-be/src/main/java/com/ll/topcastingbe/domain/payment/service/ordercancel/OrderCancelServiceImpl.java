package com.ll.topcastingbe.domain.payment.service.ordercancel;

import com.ll.topcastingbe.domain.order.entity.OrderStatus;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.payment.entity.OrderCancel;
import com.ll.topcastingbe.domain.payment.entity.Payment;
import com.ll.topcastingbe.domain.payment.repository.PaymentRepository;
import com.ll.topcastingbe.domain.payment.repository.ordercancel.OrderCancelRepository;
import com.ll.topcastingbe.domain.payment.service.TossPaymentConfig;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderCancelServiceImpl implements OrderCancelService {
    private final PaymentRepository paymentRepository;
    private final OrderCancelRepository orderCancelRepository;
    private final TossPaymentConfig tossPaymentConfig;

    @Override
    @Transactional
    public void tossPaymentCancel(final String paymentKey, final String cancelReason) {

        requestCancelPaymentAccept(paymentKey, cancelReason);

        final Payment payment = paymentRepository.findByPaymentKey(paymentKey)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.INVALID_INPUT_VALUE));
        final OrderCancel orderCancel = OrderCancel.builder()
                .payment(payment)
                .reason(cancelReason)
                .build();

        orderCancelRepository.save(orderCancel);
        payment.getOrder().modifyOrderStatus(OrderStatus.REFUND);
    }

    private void requestCancelPaymentAccept(final String paymentKey, final String cancelReason) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tossPaymentConfig.getTossCancelApiuri() + paymentKey + "/cancel"))
                .header("Authorization", tossPaymentConfig.getTestTossAuthorizationHeader())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"cancelReason\":\"" + cancelReason + "\"}"))
                .build();
        final HttpResponse<String> response = sendTossPaymentCancelRequest(request);

        final int statusCode = response.statusCode();
        if (statusCode != HttpStatus.OK.value()) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT_VALUE);
        }
    }

    private HttpResponse<String> sendTossPaymentCancelRequest(final HttpRequest request) {
        try {
            HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return httpResponse;
        } catch (IOException ioe) {
            throw new IllegalArgumentException("IoException");
        } catch (InterruptedException ie) {
            throw new IllegalArgumentException("InterruptedException ie");
        }
    }
}
