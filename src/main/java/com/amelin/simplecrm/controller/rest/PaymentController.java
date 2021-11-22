package com.amelin.simplecrm.controller.rest;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.payments.Payment;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.dao.repository.PaymentRepo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class PaymentController {

    private final PaymentRepo payments;

    public PaymentController(PaymentRepo payments) {
        this.payments = payments;
    }



}
