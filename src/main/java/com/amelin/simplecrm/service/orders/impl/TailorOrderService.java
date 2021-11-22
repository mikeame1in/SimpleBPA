package com.amelin.simplecrm.service.orders.impl;

import com.amelin.simplecrm.dao.orders.OrderDao;
import com.amelin.simplecrm.dao.orders.impl.TailorOrderDao;
import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.StMachine;
import com.amelin.simplecrm.domain.orders.statemachine.impl.OrderStateMachine;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import com.amelin.simplecrm.domain.orders.statemachine.state.OrderState;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.service.orders.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TailorOrderService implements OrderService {

//    private StMachine machine;
    private TailorOrderDao orders;

    public TailorOrderService(TailorOrderDao orders) {
        this.orders = orders;
    }

    @Override
    public Order createOrder(String type) {
        return new TailorOrder();
    }

    @Override
    public Order saveOrder(Order order) {
        OrderState orderState = defineYourStateByStateOfJobs((TailorOrder) order);
        order.setState(orderState);
        return this.orders.save(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return this.orders.findAll();
    }

    @Override
    public Order findOrder(Long orderId) {
        return orders.getById(orderId);
    }

    @Override
    public Order updateOrder(Order order) {
        return orders.save(order);
    }

    private OrderState defineYourStateByStateOfJobs(TailorOrder order) {

        JobState priorityJobState = JobState.NEW;

        List<Job> jobs =  new ArrayList<>(order.getJobs());
        for (Job job: jobs) {
            JobState jobState = job.getState();
            if (jobState.getPriority() < priorityJobState.getPriority()) {
                priorityJobState = jobState;
            }
        }

        OrderState orderState = OrderState.findState(priorityJobState.getPriority());
        if (orderState.equals(OrderState.WAIT_PAYMENT)) {
            Integer debt = order.calcDebt();
            if (debt == 0) {
                orderState = OrderState.FINISH;
            }
        }

        return orderState;
    }

}
