package com.amelin.simplecrm.dao.repository;

import com.amelin.simplecrm.domain.orders.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {
    List<Job> findByOrderId(Long orderId);
    Optional<Job> findById(Long id);
    Job getById(Long id);
}
