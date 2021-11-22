package com.amelin.simplecrm.domain.orders.statemachine.guard;

import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.statemachine.event.OrderEvent;
import com.amelin.simplecrm.domain.orders.statemachine.state.OrderState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class JobStateCheckingGuard implements Guard<OrderState, OrderEvent> {
    @Override
    public boolean evaluate(StateContext<OrderState, OrderEvent> stateContext) {
        Order order = stateContext.getExtendedState().get("order", Order.class);



        return false;
    }
}
