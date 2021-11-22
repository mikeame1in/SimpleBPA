package com.amelin.simplecrm.dao.orders;

import com.amelin.simplecrm.domain.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findAll();
    Order getById(Long id);
    Order save(Order order);
}
