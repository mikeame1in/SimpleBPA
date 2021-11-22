package com.amelin.simplecrm.controller;

import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.Customer;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.dao.repository.CustomerRepo;
import com.amelin.simplecrm.domain.users.User;
import com.amelin.simplecrm.service.orders.impl.TailorOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class TailorOrderController {

    private static final String VIEWS_ORDERS_CREATE_OR_UPDATE_FORM = "orders/createOrUpdateOrderForm";

    private TailorOrderService orders;
    private CustomerRepo customers;

    public TailorOrderController(TailorOrderService orders, CustomerRepo customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @GetMapping("/customers/{customerId}/tailorOrders/new")
    public String initNewOrderForm(@PathVariable(value = "customerId") Customer customer,
                                   ModelMap model) {
        TailorOrder order = new TailorOrder();
        order.setCustomer(customer);
        model.addAttribute("order", order);
        model.addAttribute("customer", customer);
        return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/customers/{customerId}/tailorOrders/new")
    public String processNewOrderForm(TailorOrder order, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
        }
        else {
            this.orders.saveOrder(order);
            return "redirect:/customers/{customerId}/tailorOrders/" + order.getId();
        }
    }

    @GetMapping("/customers/{customerId}/tailorOrders/{orderId}/edit")
    public String initUpdateForm(@PathVariable(value = "customerId") Customer customer,
                                 @PathVariable("orderId") Long orderId,
                                 Map<String, Object> model) {
        Order order = this.orders.findOrder(orderId);
        model.put("order", order);
        model.put("customer", customer);
        return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/customers/{customerId}/tailorOrders/{orderId}/edit")
    public String processUpdateForm(@AuthenticationPrincipal User user,
                                    @PathVariable("customerId") Customer customer,
                                    TailorOrder order,
                                    Map<String, Object> model) {
//        BindingResult result
//        if (result.hasErrors()) {
//            order.setCustomer(customer);
//              model.put("pet", order);
//              return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
//        }
//        else {
////
//
//        }
//                customer.addOrder(order);
                    order.setCustomer(customer);
                this.orders.saveOrder(order);
                return "redirect:/customers/{customerId}/tailorOrders/"+ order.getId();
    }

    @GetMapping("/customers/{customerId}/tailorOrders/{orderId}")
    public ModelAndView showOrder(@PathVariable("customerId") Long customerId,
                                  @PathVariable("orderId") Long orderId) {
        ModelAndView mav = new ModelAndView("orders/orderDetails");
        Order order = this.orders.findOrder(orderId);
        mav.addObject(order);
        return mav;
    }

    @GetMapping("/tailorOrders/list")
    public ModelAndView showOrderList() {
        ModelAndView mav = new ModelAndView("orders/orderList");
        List<Order> orders = this.orders.findAllOrders();
        mav.addObject(orders);
        return mav;
    }
}
