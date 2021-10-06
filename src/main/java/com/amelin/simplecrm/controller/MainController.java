package com.amelin.simplecrm.controller;

import com.amelin.simplecrm.domain.order.Employee;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {

        return "greeting";
    }

    @GetMapping("/order")
    public String getOrder(@RequestParam Map<String,String> allRequestParams, Map<String, Object> model) {

        return "order";
    }

//    @GetMapping("/orders")
//    public String getOrders(@RequestParam Map<String,String> allRequestParams, Map<String, Object> model) {
//        Employee employee_1 = new Employee("Иван", "Иванович", "Иванов");
//        Employee employee_2 = new Employee("Петр", "Иванович", "Иванов");
//        Employee employee_3 = new Employee("Иван", "Иванович", "Петров");
//
//        List<Employee> employeeList = new ArrayList<Employee>();
//        employeeList.add(employee_1);
//        employeeList.add(employee_2);
//        employeeList.add(employee_3);
//
//        Iterable<Employee> employees = employeeList;
//
//        model.put("employees", employees);
//        return "orders";
//    }

        @PostMapping("/order/add")
    public String createOrder(@RequestParam Map<String,String> allRequestParams, Map<String, Object> model) {

        return "order";
    }
}
