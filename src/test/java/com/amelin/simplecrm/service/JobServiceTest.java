package com.amelin.simplecrm.service;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import com.amelin.simplecrm.dao.repository.JobRepo;
import com.amelin.simplecrm.service.jobs.JobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobServiceTest {
    private static Long TEST_JOB_ID = new Long(1);

    @Autowired
    private JobService jobService;

    @MockBean
    private JobRepo jobs;

    private Job job;

    @Before
    public void setup() {
        job = new Job();
        job.setId(TEST_JOB_ID);
        job.setState(JobState.NEW);
        job.setNote("test");

        Mockito.doReturn(job).when(jobs).getById(TEST_JOB_ID);
        Mockito.doReturn(job).when(jobs).save(job);
    }

    @Test
    public void ShouldCreateNewJob() {
        Job job = jobService.createJob();
        assertTrue(job.isNew());
    }

    @Test
    public void ShouldStayNew() {
        jobService.updateJob(TEST_JOB_ID, null);
        assertTrue(job.getState().equals(JobState.NEW));
    }

    @Test
    public void ShouldStayOld() {
        job.setNote("shouldStayOld");
        jobService.updateJob(TEST_JOB_ID, JobEvent.SUSPENDED);
        assertTrue(job.getState().equals(JobState.NEW));

        jobService.updateJob(TEST_JOB_ID, JobEvent.DONE);
        assertTrue(job.getState().equals(JobState.NEW));
    }


    @Test
    public void ShouldTakeToWorkJob() {
        jobService.updateJob(TEST_JOB_ID, JobEvent.TAKEN_TO_WORK);
        assertTrue(job.getState().equals(JobState.IN_WORK));
    }

    @Test
    public void ShouldSuspendedJob() {
        jobService.updateJob(TEST_JOB_ID, JobEvent.TAKEN_TO_WORK);
        jobService.updateJob(TEST_JOB_ID, JobEvent.SUSPENDED);
        assertTrue(job.getState().equals(JobState.WAIT));
    }

    @Test
    public void ShouldAppearErrorWhileTheNewToWaitTransition() {
        jobService.updateJob(TEST_JOB_ID, JobEvent.SUSPENDED);
        assertTrue(!job.getState().equals(JobState.WAIT) & job.getState().equals(JobState.NEW));
    }

    @Test
    public void ShouldBeSuccessfullyTheWaitToInWorkTransition() {
        jobService.updateJob(TEST_JOB_ID, JobEvent.TAKEN_TO_WORK);
        jobService.updateJob(TEST_JOB_ID, JobEvent.SUSPENDED);
        jobService.updateJob(TEST_JOB_ID, JobEvent.RENEWED);
        assertTrue(job.getState().equals(JobState.IN_WORK));
    }

    @Test
    public void ShouldBeSuccessfullyTheInWorkToReadyTransition() {
        jobService.updateJob(TEST_JOB_ID, JobEvent.TAKEN_TO_WORK);
        jobService.updateJob(TEST_JOB_ID, JobEvent.DONE);
        assertTrue(job.getState().equals(JobState.READY));
    }

    @Test
    public void ShouldAppearErrorWhileTheNewToReadyTransition() {
        jobService.updateJob(TEST_JOB_ID, JobEvent.DONE);
        assertTrue(!job.getState().equals(JobState.READY) & job.getState().equals(JobState.NEW));
    }

    @Test
    public void ShouldAppearErrorWhileTheWaitToReadyTransition() {
        jobService.updateJob(TEST_JOB_ID, JobEvent.TAKEN_TO_WORK);
        jobService.updateJob(TEST_JOB_ID, JobEvent.SUSPENDED);
        jobService.updateJob(TEST_JOB_ID, JobEvent.DONE);
        assertTrue(!job.getState().equals(JobState.READY) & job.getState().equals(JobState.WAIT));
    }
}
