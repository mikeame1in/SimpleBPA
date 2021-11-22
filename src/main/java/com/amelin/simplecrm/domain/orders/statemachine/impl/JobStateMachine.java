package com.amelin.simplecrm.domain.orders.statemachine.impl;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.StMachine;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class JobStateMachine implements StMachine<JobEvent, Job> {
    public static final String stateMachineId = "jobStateMachine";

    private StateMachineFactory<JobState, JobEvent> factory;
    private StateMachinePersister<JobState, JobEvent, Job> persister;

    public JobStateMachine(StateMachineFactory<JobState, JobEvent> factory,
                           StateMachinePersister<JobState, JobEvent, Job> persister) {
        this.factory = factory;
        this.persister = persister;
    }

    public synchronized boolean sendEvent(Message<JobEvent> message, Job job) {
        boolean result = false;
        StateMachine<JobState, JobEvent> jobStateMachine = factory.getStateMachine(stateMachineId);
        try {
            jobStateMachine.start();
            persister.restore(jobStateMachine, job);
            // Add latency for thread safety testing
            Thread.sleep(1000);
            result = jobStateMachine.sendEvent(message);
            persister.persist(jobStateMachine, job);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobStateMachine.stop();
        }
        return result;
    }
}
