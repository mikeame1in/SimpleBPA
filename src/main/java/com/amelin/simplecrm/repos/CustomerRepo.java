package com.amelin.simplecrm.repos;

import com.amelin.simplecrm.domain.order.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
}
