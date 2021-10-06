package com.amelin.simplecrm.repos;

import com.amelin.simplecrm.domain.order.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Long> {
    Order findByOrderNumber(Integer orderNumber);
}
