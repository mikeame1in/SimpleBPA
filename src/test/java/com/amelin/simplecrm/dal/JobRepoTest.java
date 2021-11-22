package com.amelin.simplecrm.dal;

import com.amelin.simplecrm.domain.orders.Customer;
import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.dao.repository.JobRepo;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class JobRepoTest {

    @Autowired
    private JobRepo jobs;

    private Job job;

    @Before
    public void setup() {
        Job job = new Job();
        job.setNote("test");
        job.setState(JobState.IN_WORK);

        this.job = this.jobs.save(job);
    }

    @Test
    public void shouldFindJobById() {
        Job job = this.jobs.findById(this.job.getId()).get();
        assertThat(job.getNote() != null);
    }

    @Test
    public void shouldDefineStateHowInWork() {
        Job job = this.jobs.findById(this.job.getId()).get();
        assertThat(job.getState().equals(JobState.IN_WORK));
    }
}
