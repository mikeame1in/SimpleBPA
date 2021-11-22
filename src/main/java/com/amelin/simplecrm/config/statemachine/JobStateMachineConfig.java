package com.amelin.simplecrm.config.statemachine;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.*;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

import static com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent.*;
import static com.amelin.simplecrm.domain.orders.statemachine.state.JobState.*;

@Configuration
@EnableStateMachineFactory(name = "jobStateMachineFactory")
public class JobStateMachineConfig extends EnumStateMachineConfigurerAdapter<JobState, JobEvent> {
    public static final String jobStateMachineId = "jobStateMachineId";

    @Override
    public void configure(final StateMachineConfigurationConfigurer<JobState, JobEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(final StateMachineStateConfigurer<JobState, JobEvent> states) throws Exception {
        states
                .withStates()
                .initial(NEW)
                .end(READY)
                .states(EnumSet.allOf(JobState.class));
    }

    @Override
    public void configure(final StateMachineTransitionConfigurer<JobState, JobEvent> transitions) throws Exception {
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
                .target(READY)
                .event(DONE)

                .and()
                .withExternal()
                .source(WAIT)
                .target(IN_WORK)
                .event(RENEWED);
    }

    @Bean
    public StateMachinePersister<JobState, JobEvent, Job> persister(){
        return new DefaultStateMachinePersister<>(new StateMachinePersist<JobState, JobEvent, Job>() {
            @Override
            public void write(StateMachineContext<JobState, JobEvent> context, Job job) throws Exception {
                job.setState(context.getState());
            }

            @Override
            public StateMachineContext<JobState, JobEvent> read(Job job) throws Exception {
                StateMachineContext<JobState, JobEvent> result =
                        new DefaultStateMachineContext<>(
                                job.getState(),
                                null,
                                null,
                                null,
                                null,
                                jobStateMachineId
                        );
                return result;
            }
        });
    }
}
