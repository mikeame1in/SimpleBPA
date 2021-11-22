package com.amelin.simplecrm.service.jobs.impl;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.StMachine;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import com.amelin.simplecrm.domain.orders.statemachine.impl.JobStateMachine;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import com.amelin.simplecrm.dao.repository.JobRepo;
import com.amelin.simplecrm.service.jobs.JobService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("all")
public class JobServiceImpl implements JobService {

    private StMachine machine;
    private JobRepo jobs;

    public JobServiceImpl(JobRepo jobs, JobStateMachine machine) {
        this.jobs = jobs;
        this.machine = machine;
    }

    @Override
    public Job createJob() {
        return new Job();
    }

    @Override
    public Job saveJob(Job job) {
        job.setState(JobState.NEW);
        job = this.jobs.save(job);
        return job;
    }

    @Override
    public List<Job> getJobs(Long orderId) {
        return this.jobs.findByOrderId(orderId);
    }

    @Override
    public Job getJob(Long jobId) {
        return this.jobs.findById(jobId).get();
    }

    @Override
    public boolean updateJob(Long jobId, JobEvent event) {
        Job job = this.jobs.getById(jobId);
        Message message = MessageBuilder.withPayload(event).setHeader("job", job).build();
        if (!machine.sendEvent(message, job)) {
            System.out.println("threadName=" + Thread.currentThread().getName() +
                    "  Failure update job,  Abnormal state  id=" + jobId);
            return false;
        }
        else {
            this.jobs.save(job);
            return true;
        }
    }

    @Override
    public void deleteJob(Job job) {
        this.jobs.delete(job);
    }
}
