package com.amelin.simplecrm.repos;

import com.amelin.simplecrm.domain.order.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {
}
