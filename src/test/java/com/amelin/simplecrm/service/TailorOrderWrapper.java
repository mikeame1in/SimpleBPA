package com.amelin.simplecrm.service;

import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.domain.payments.Payment;

public class TailorOrderWrapper extends TailorOrder {

    public void addPayment(Payment payment) {
        super.addPayment(payment);
    }
}
