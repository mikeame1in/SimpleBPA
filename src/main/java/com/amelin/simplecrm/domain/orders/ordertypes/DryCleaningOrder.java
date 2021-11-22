package com.amelin.simplecrm.domain.orders.ordertypes;

import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.Customer;

import javax.persistence.*;

@Entity
@Table(name = "cleaning_orders")
public class DryCleaningOrder extends Order {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Integer calcCost() {
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public boolean isPaid() {
        return false;
    }

    @Override
    public void pay() {

    }

    @Override
    public Integer calcDebt() {
        return null;
    }

    @Override
    public Integer getOrderCloseDate() {
        return null;
    }
}
