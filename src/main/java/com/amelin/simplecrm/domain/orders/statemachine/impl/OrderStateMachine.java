package com.amelin.simplecrm.domain.orders.statemachine.impl;

import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.StMachine;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import com.amelin.simplecrm.domain.orders.statemachine.event.OrderEvent;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import com.amelin.simplecrm.domain.orders.statemachine.state.OrderState;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

//@Component
//@SuppressWarnings("all")
public class OrderStateMachine implements StMachine<OrderEvent, Order> {
    public static final String stateMachineId = "orderStateMachine";

    private StateMachineFactory<OrderState, OrderEvent> factory;
    private StateMachinePersister<OrderState, OrderEvent, Order> persister;

    public OrderStateMachine(StateMachineFactory<OrderState, OrderEvent> factory,
                             StateMachinePersister<OrderState, OrderEvent, Order> persister) {
        this.factory = factory;
        this.persister = persister;
    }

    @Override
    public boolean sendEvent(Message<OrderEvent> message, Order order) {
        boolean result = false;
        StateMachine<OrderState, OrderEvent> orderStateMachine = factory.getStateMachine(stateMachineId);
        try {
            orderStateMachine.start();
            persister.restore(orderStateMachine, order);
            // Add latency for thread safety testing
            Thread.sleep(1000);
            result = orderStateMachine.sendEvent(message);
            persister.persist(orderStateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}
