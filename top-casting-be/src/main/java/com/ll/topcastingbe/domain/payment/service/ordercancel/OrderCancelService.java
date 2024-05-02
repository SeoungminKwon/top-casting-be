package com.ll.topcastingbe.domain.payment.service.ordercancel;

import java.io.IOException;

public interface OrderCancelService {
    void tossPaymentCancel(final String paymentKey, final String cancelReason) throws IOException, InterruptedException;
}
