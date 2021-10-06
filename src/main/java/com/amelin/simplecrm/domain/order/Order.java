package com.amelin.simplecrm.domain.order;

import com.amelin.simplecrm.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

import java.sql.Date;
import java.util.Map;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer orderNumber;
    private Date acceptanceDate;
    private Date completionDate;
    private Date issueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Transient
    private Employee acceptor;

    public Order() {
    }

    public Order(Map<String, String> allRequestParams) {
        this.orderNumber = Integer.parseInt(allRequestParams.get("orderNumber"));
        this.acceptanceDate = Date.valueOf(allRequestParams.get("acceptanceDate"));
        this.completionDate = Date.valueOf(allRequestParams.get("completionDate"));

        customer = Customer.find(allRequestParams.get("email"));
        if (customer.getEmail() == null) {
            Customer.insert(allRequestParams);
        }
    }

    public void updateOrder(Map<String, String> allRequestParams) {
        this.orderNumber = Integer.parseInt(allRequestParams.get("orderNumber"));
        this.acceptanceDate = Date.valueOf(allRequestParams.get("acceptanceDate"));
        this.completionDate = Date.valueOf(allRequestParams.get("completionDate"));

        customer = Customer.find(allRequestParams.get("email"));
        if (customer.getEmail() == null) {
            Customer.insert(allRequestParams);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

}