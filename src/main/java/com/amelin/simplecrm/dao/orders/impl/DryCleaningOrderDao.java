package com.amelin.simplecrm.dao.orders.impl;

import com.amelin.simplecrm.dao.orders.OrderDao;
import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.ordertypes.DryCleaningOrder;
import com.amelin.simplecrm.dao.repository.DryCleaningOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DryCleaningOrderDao implements OrderDao {

    @Autowired
    private DryCleaningOrderRepo orders;

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
        return this.orders.getById(id);
    }

    @Override
    public Order save(Order order) {
        return this.orders.save((DryCleaningOrder) order);
    }
}
