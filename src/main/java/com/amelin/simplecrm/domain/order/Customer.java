package com.amelin.simplecrm.domain.order;

import com.amelin.simplecrm.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;

    @Transient
    @Autowired
    private static CustomerRepo customerRepo;

    public Customer() {
    }

    public Customer(Map<String, String> allRequestParams) {
        this.firstName = allRequestParams.get("firstName");
        this.lastName = allRequestParams.get("lastName");
        this.middleName = allRequestParams.get("middleName");
        this.phone = allRequestParams.get("phone");
        this.email = allRequestParams.get("email");
    }

    public static Customer insert(Map<String, String> allRequestParams){
        customerRepo.save(new Customer(allRequestParams));

        return Customer.find(allRequestParams.get("email"));
    }

    public static Customer find(String email) {
        Customer customer;

        try {
            customer = customerRepo.findByEmail(email);
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
            customer = new Customer();
        }
        return customer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
