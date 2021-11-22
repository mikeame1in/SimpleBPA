package com.amelin.simplecrm.domain.orders.ordertypes;

import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.Customer;
import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.payments.Payment;
import com.amelin.simplecrm.domain.users.User;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tailor_orders")
public class TailorOrder extends Order {

    @ManyToOne
    @JoinColumn(name = "wear_degree")
    private WearDegree wearDegree;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Transient
    private Set<Job> jobs = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "acceptor_id")
    private User acceptor;

    public TailorOrder() {
    }

    public WearDegree getWearDegree() {
        return wearDegree;
    }

    public void setWearDegree(WearDegree wearDegree) {
        this.wearDegree = wearDegree;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Job> getJobs() {
        return getJobsInternal();
    }

    protected Set<Job> getJobsInternal() {
        if (this.jobs == null) {
            this.jobs = new HashSet<>();
        }
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    protected void addPayment(Payment payment) {
        payments.add(payment);
    }

    public User getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(User acceptor) {
        this.acceptor = acceptor;
    }

    public void addJob(Job job) {
        if (job.isNew()) {
            getJobsInternal().add(job);
        }
        job.setOrderId(this.getId());
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    @Override
    public Integer calcCost() {
        Integer sum = new Integer(0);

        for (Job job : this.jobs) {
            sum += job.getPrice();
        }

        return sum;
    }

    @Override
    public boolean isPaid() {
        return false;
    }

    @Override
    public Integer calcDebt() {
        Integer paidPart = new Integer(0);
        Integer cost = calcCost();

        try{
            for (Payment payment : this.payments) {
                paidPart += payment.getSum();
            }
            return cost - paidPart;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return cost;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public Integer getOrderCloseDate() {
        return null;
    }

    @Override
    public void pay() {

    }
}