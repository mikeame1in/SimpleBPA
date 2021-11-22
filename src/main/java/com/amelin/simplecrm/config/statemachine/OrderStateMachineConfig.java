package com.amelin.simplecrm.config.statemachine;

import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import com.amelin.simplecrm.domain.orders.statemachine.event.OrderEvent;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import com.amelin.simplecrm.domain.orders.statemachine.state.OrderState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

import static com.amelin.simplecrm.domain.orders.statemachine.event.OrderEvent.*;
import static com.amelin.simplecrm.domain.orders.statemachine.state.OrderState.*;


//@Configuration
//@EnableStateMachineFactory(name = "orderStateMachineFactory")
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderState, OrderEvent> {
    public static final String orderStateMachineId = "orderStateMachineId";

    @Override
    public void configure(final StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(final StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states
                .withStates()
                .initial(NEW)
                .end(FINISH)
                .states(EnumSet.allOf(OrderState.class));
    }

    @Override
    public void configure(final StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(NEW)
                .target(IN_WORK)
                .event(TAKEN_TO_WORK)

                .and()
                .withExternal()
                .source(IN_WORK)
                .target(WAIT)
                .event(SUSPENDED)

                .and()
                .withExternal()
                .source(IN_WORK)
                .target(WAIT_PAYMENT)
                .event(DONE)

                .and()
                .withExternal()
                .source(WAIT)
                .target(IN_WORK)
                .event(RENEWED)

                .and()
                .withExternal()
                .source(WAIT_PAYMENT)
                .target(FINISH)
                .event(PAYED);
    }

//    @Bean
//    public StateMachinePersister<OrderState, OrderEvent, Order> persister(){
//        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderState, OrderEvent, Order>() {
//            @Override
//            public void write(StateMachineContext<OrderState, OrderEvent> context, Order order) throws Exception {
//                order.setState(context.getState());
//            }
//
//            @Override
//            public StateMachineContext<OrderState, OrderEvent> read(Order order) throws Exception {
//                StateMachineContext<OrderState, OrderEvent> result =
//                        new DefaultStateMachineContext<>(
//                                order.getState(),
//                                null,
//                                null,
//                                null,
//                                null,
//                                orderStateMachineId
//                        );
//                return result;
//            }
//        });
//    }
}
