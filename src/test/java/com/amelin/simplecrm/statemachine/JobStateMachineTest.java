package com.amelin.simplecrm.statemachine;

import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import static com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent.*;
import static com.amelin.simplecrm.domain.orders.statemachine.state.JobState.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("all")
public class JobStateMachineTest {

    @Autowired
    private StateMachineFactory<JobState, JobEvent> factory;

    @Test
    public void testWhenTakenToWork() throws Exception {
        StateMachine<JobState, JobEvent> machine = factory.getStateMachine();
        StateMachineTestPlan<JobState, JobEvent> plan =
                StateMachineTestPlanBuilder.<JobState, JobEvent>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(NEW)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(TAKEN_TO_WORK)
                        .expectState(IN_WORK)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testWhenSuspended() throws Exception {
        StateMachine<JobState, JobEvent> machine = factory.getStateMachine();
        StateMachineTestPlan<JobState, JobEvent> plan =
                StateMachineTestPlanBuilder.<JobState, JobEvent>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(NEW)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(TAKEN_TO_WORK)
                        .expectState(IN_WORK)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(SUSPENDED)
                        .expectState(WAIT)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testWhenRenewed() throws Exception {
        StateMachine<JobState, JobEvent> machine = factory.getStateMachine();
        StateMachineTestPlan<JobState, JobEvent> plan =
                StateMachineTestPlanBuilder.<JobState, JobEvent>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(NEW)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(TAKEN_TO_WORK)
                        .expectState(IN_WORK)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(SUSPENDED)
                        .expectState(WAIT)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(RENEWED)
                        .expectState(IN_WORK)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testWhenDone() throws Exception {
        StateMachine<JobState, JobEvent> machine = factory.getStateMachine();
        StateMachineTestPlan<JobState, JobEvent> plan =
                StateMachineTestPlanBuilder.<JobState, JobEvent>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(NEW)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(TAKEN_TO_WORK)
                        .expectState(IN_WORK)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(DONE)
                        .expectState(READY)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testWhenRenewedAndThenDone() throws Exception {
        StateMachine<JobState, JobEvent> machine = factory.getStateMachine();
        StateMachineTestPlan<JobState, JobEvent> plan =
                StateMachineTestPlanBuilder.<JobState, JobEvent>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(NEW)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(TAKEN_TO_WORK)
                        .expectState(IN_WORK)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(SUSPENDED)
                        .expectState(WAIT)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(RENEWED)
                        .expectState(IN_WORK)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(DONE)
                        .expectState(READY)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }
}
