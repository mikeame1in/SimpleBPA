package com.amelin.simplecrm.service;

import com.amelin.simplecrm.domain.order.Article;
import com.amelin.simplecrm.domain.order.Order;
import com.amelin.simplecrm.repos.ArticleRepo;
import com.amelin.simplecrm.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ArticleRepo articleRepo;

    public void addOrder(Map<String, String> allRequestParams) {
        orderRepo.save(new Order(allRequestParams));

        Order order = orderRepo
                .findByOrderNumber(Integer.parseInt(allRequestParams.get("orderNumber")));

        articleRepo.save(new Article(allRequestParams, order));
    }

    public void updateOrder(Map<String, String> allRequestParams){
        Order order = orderRepo
                .findByOrderNumber(Integer.parseInt(allRequestParams.get("orderNumber")));
        order.updateOrder(allRequestParams);

        orderRepo.save(order);
    }

    public void deleteOrder(Map<String, String> allRequestParams){
        Order order = orderRepo
                .findByOrderNumber(Integer.parseInt(allRequestParams.get("orderNumber")));

        orderRepo.delete(order);
    }

    public Order findOrder(Integer orderNumber) {
        return orderRepo.findByOrderNumber(orderNumber);
    }

    public List<Order> findAllOrders() {
        return (List<Order>)orderRepo.findAll();
    }

}
