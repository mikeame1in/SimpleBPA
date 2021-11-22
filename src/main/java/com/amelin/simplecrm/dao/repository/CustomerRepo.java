package com.amelin.simplecrm.dao.repository;

import com.amelin.simplecrm.domain.orders.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

    Optional<Customer> findById(Long customerId);

//    @Query("SELECT DISTINCT customer FROM Customer customer left join  customer.articles WHERE customer.lastName LIKE :lastName% ")
    @Transactional(readOnly = true)
    Page<Customer> findByLastName(@Param("lastName") String lastName, Pageable pageable);
}
