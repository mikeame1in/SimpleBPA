package com.amelin.simplecrm.service.payments.impl;

import com.amelin.simplecrm.domain.payments.Payment;
import com.amelin.simplecrm.domain.payments.PaymentMethod;
import com.amelin.simplecrm.dao.repository.PaymentRepo;
import com.amelin.simplecrm.service.payments.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepo payments;

    @Override
    public boolean makePayment(Integer sum, Long orderId, PaymentMethod method) {
        Payment payment = new Payment();
        payment.setSum(sum);
//        payment.setOrder(orderId);
        payment.setPaymentMethod(method);

        payment = this.payments.save(payment);

        if (!payment.isNew()) {
            return true;
        }
        else {
            return false;
        }
    }
}
