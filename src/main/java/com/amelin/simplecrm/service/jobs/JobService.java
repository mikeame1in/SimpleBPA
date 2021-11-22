package com.amelin.simplecrm.service.jobs;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;

import java.util.List;

public interface JobService {
    public Job createJob();
    public Job saveJob(Job job);
    public List<Job> getJobs(Long orderId);
    public Job getJob(Long jobId);
    public boolean updateJob(Long jobId, JobEvent event);
    public void deleteJob(Job job);
}
