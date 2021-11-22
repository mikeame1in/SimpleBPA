package com.amelin.simplecrm.dal;

import com.amelin.simplecrm.dao.repository.CustomerRepo;
import com.amelin.simplecrm.dao.repository.JobRepo;
import com.amelin.simplecrm.domain.orders.Customer;
import com.amelin.simplecrm.domain.orders.Job;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class CustomerRepoTest {

    @Autowired
    private CustomerRepo customers;

    private Customer customer;

    @Before
    public void setup() {
        Customer customer = new Customer();
        customer.setFirstName("Mike");
        customer.setEmail("test@mail.ru");

        this.customer = this.customers.save(customer);
    }

    @Test
    public void shouldFindJobById() {
        Customer customer = this.customers.findById(this.customer.getId()).get();
        assertThat(customer.getFirstName() != null);
    }

    @Test
    public void shouldFindJobs() {
        List<Customer> customers = this.customers.findAll();
        customers.size();
    }
}
