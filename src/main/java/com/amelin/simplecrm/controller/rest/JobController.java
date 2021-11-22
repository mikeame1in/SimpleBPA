package com.amelin.simplecrm.controller.rest;

import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.users.User;
import com.amelin.simplecrm.dto.JobEventDto;
import com.amelin.simplecrm.service.jobs.impl.JobServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("job")
public class JobController {

    private final JobServiceImpl jobs;

    public JobController(JobServiceImpl jobs) {
        this.jobs = jobs;
    }

    @GetMapping
    public List<Job> list(@RequestParam Long orderId) {
        return jobs.getJobs(orderId);
    }

    @GetMapping("{id}")
    public ResponseEntity<Job> getOne(@PathVariable("id") Long jobId) {
        Job job = jobs.getJob(jobId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PostMapping
    public Job create(@RequestBody Job job) throws IOException {
        return jobs.saveJob(job);
    }

    @PutMapping("{id}")
    public boolean update(@PathVariable("id") Job job, @RequestBody JobEventDto jobEventDto) throws IOException {
        return jobs.updateJob(job.getId(), jobEventDto.getCurrentEvent());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Job job) {
        jobs.deleteJob(job);
    }
}
