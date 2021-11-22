package com.amelin.simplecrm.controller;

import com.amelin.simplecrm.domain.orders.Customer;
import com.amelin.simplecrm.dao.repository.CustomerRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {
    private static final String VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM = "customers/createOrUpdateCustomerForm";

    private final CustomerRepo customers;
//    private final JobRepo jobs;

//    public CustomerController(CustomerRepo customers, JobRepo jobs) {
//        this.customers = customers;
//        this.jobs = jobs;
//    }

    public CustomerController(CustomerRepo customers) {
        this.customers = customers;
    }

    @InitBinder
    public void SetAllowedFields(WebDataBinder dataBinder){

    }

    @GetMapping("/customers/new")
    public String initCreationForm(Map<String, Object> model) {
        Customer customer = new Customer();
        model.put("customer", customer);
        return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/customers/new")
    public String processCreationForm(@Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
        }
        else {
            this.customers.save(customer);
            return "redirect:/customers/" + customer.getId();
        }
    }

    @GetMapping("/customers/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("customer", new Customer());
        return "customers/findCustomers";
    }

    @GetMapping("/customers")
    public String processFindForm(@RequestParam(defaultValue = "1") int page, Customer customer, BindingResult result,
                                  Model model) {

        if (customer.getLastName() == null) {
            customer.setLastName("");
        }

        String lastName = customer.getLastName();
        Page<Customer> customersResults = findPaginatedForOwnersLastName(page, lastName);
        if (customersResults.isEmpty()) {

            result.rejectValue("lastName", "notFound", "not found");
            return "customers/findCustomers";
        }
        else if (customersResults.getTotalElements() == 1) {
            customer = customersResults.iterator().next();
            return "redirect:/customers/" + customer.getId();
        }
        else {
            lastName = customer.getLastName();
            return addPaginationModel(page, model, lastName, customersResults);
        }
    }

    private String addPaginationModel(int page, Model model, String lastName, Page<Customer> paginated) {
//        model.addAttribute("listCustomers", paginated);
        List<Customer> listCustomers = paginated.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginated.getTotalPages());
        model.addAttribute("totalItems", paginated.getTotalElements());
        model.addAttribute("listCustomers", listCustomers);
        return "customers/customersList";
    }

    private Page<Customer> findPaginatedForOwnersLastName(int page, String lastname) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return customers.findByLastName(lastname, pageable);
    }

    @GetMapping("/customers/{customerId}/edit")
    public String initUpdateCustomerForm(@PathVariable("customerId") Long customerId, Model model) {
        Customer customer = this.customers.findById(customerId).get();
        model.addAttribute(customer);
        return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/customers/{customerId}/edit")
    public String processUpdateCustomerForm(@Valid Customer customer, BindingResult result,
                                         @PathVariable("customerId") Long customerId) {
        if (result.hasErrors()) {
            return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
        }
        else {
            customer.setId(customerId);
            this.customers.save(customer);
            return "redirect:/customers/" + customer.getId();
        }
    }

    @GetMapping("/customers/{customerId}")
    public ModelAndView showCustomer(@PathVariable("customerId") Long customerId) {
        ModelAndView mav = new ModelAndView("customers/customerDetails");
        Customer customer = this.customers.findById(customerId).get();
//        for (TailorOrder tailorOrder : customer.getOrders()) {
//            tailorOrder.setJobs(new HashSet<>(jobs.findByOrderId(tailorOrder.getId())));
//        }
        mav.addObject(customer);
        return mav;
    }
}
