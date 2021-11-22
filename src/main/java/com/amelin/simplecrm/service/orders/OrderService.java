package com.amelin.simplecrm.service.orders;

import com.amelin.simplecrm.dao.orders.OrderDao;
import com.amelin.simplecrm.domain.Order;

import java.util.List;

public interface OrderService {
    public Order createOrder(String type);
    public Order saveOrder(Order order);
    public List<Order> findAllOrders();
    public Order findOrder(Long orderId);
    public Order updateOrder(Order order);
}
