package com.amelin.simplecrm.service.orders.impl;

import com.amelin.simplecrm.dao.orders.OrderDao;
import com.amelin.simplecrm.dao.orders.impl.DryCleaningOrderDao;
import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.service.orders.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DryCleaningOrderService implements OrderService {
    @Override
    public Order createOrder(String type) {
        return null;
    }

    @Override
    public Order saveOrder(Order order) {
        return null;
    }

    @Override
    public List<Order> findAllOrders() {
        return null;
    }

    @Override
    public Order findOrder(Long orderId) {
        return null;
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }
}
