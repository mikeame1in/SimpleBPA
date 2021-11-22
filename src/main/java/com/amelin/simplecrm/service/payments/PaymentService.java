package com.amelin.simplecrm.service.payments;

import com.amelin.simplecrm.domain.payments.PaymentMethod;

public interface PaymentService {
    boolean makePayment(Integer sum, Long orderId, PaymentMethod method);
}
