package com.amelin.simplecrm.domain.orders.statemachine;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import org.springframework.messaging.Message;

public interface StMachine<Event, Entity> {
    public boolean sendEvent(Message<Event> message, Entity entity);
}
