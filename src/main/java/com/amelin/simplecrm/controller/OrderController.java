package com.amelin.simplecrm.controller;

import com.amelin.simplecrm.domain.order.Order;
import com.amelin.simplecrm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public String getOrdersPage(Map<String, Object> model) {
        Iterable<Order> orders = orderService.findAllOrders();

        model.put("orders", orders);

        return "orders";
    }

    @PostMapping("/add")
    public String addOrder(@RequestParam Map<String,String> allRequestParams, Map<String, Object> model) {
        orderService.addOrder(allRequestParams);

        return "order";
    }

    @GetMapping("/manage")
    public String getOrderManagePage(@RequestParam Integer orderNumber, Map<String, Object> model) {
        orderService.findOrder(orderNumber);

        return "order";
    }

    @PostMapping("/update")
    public String updateOrder(@RequestParam Map<String,String> allRequestParams, Map<String, Object> model) {
        orderService.updateOrder(allRequestParams);

        return "order";
    }

    @PostMapping("/delete")
    public String deleteOrder(@RequestParam Map<String,String> allRequestParams, Map<String, Object> model) {
        orderService.deleteOrder(allRequestParams);

        return "order";
    }
}
