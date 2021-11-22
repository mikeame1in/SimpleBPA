package com.amelin.simplecrm.domain.payments;

import com.amelin.simplecrm.domain.BaseEntity;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    private Integer sum;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "payment_method")
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private TailorOrder order;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public TailorOrder getOrder() {
        return order;
    }

    public void setOrder(TailorOrder order) {
        this.order = order;
    }
}
