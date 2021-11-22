package com.amelin.simplecrm.domain;

import com.amelin.simplecrm.domain.orders.statemachine.state.OrderState;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Order extends Article{

    @Column(name = "acceptance_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotEmpty
    private LocalDate acceptanceDate;

    @Column(name = "order_state")
    private OrderState state;

    public LocalDate getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(LocalDate acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public abstract Integer calcCost();
    public abstract boolean isReady();
    public abstract boolean isPaid();
    public abstract void pay();
    public abstract Integer calcDebt();
    public abstract Integer getOrderCloseDate();
}
