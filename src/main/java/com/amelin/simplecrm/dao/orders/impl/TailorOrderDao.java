package com.amelin.simplecrm.dao.orders.impl;

import com.amelin.simplecrm.dao.orders.OrderDao;
import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.dao.repository.TailorOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TailorOrderDao implements OrderDao {

    @Autowired
    private TailorOrderRepo orders;

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return (List<Order>)(List<?>)this.orders.findByCustomerId(customerId);
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>)(List<?>)this.orders.findAll();
    }

    @Override
    public Order getById(Long id) {
        return this.orders.findById(id).get();
    }

    @Override
    public Order save(Order order) {
        return this.orders.save((TailorOrder) order);
    }
}
