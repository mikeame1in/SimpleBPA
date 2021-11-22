package com.amelin.simplecrm.domain.orders;

import com.amelin.simplecrm.domain.Person;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "customers")
public class Customer extends Person {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TailorOrder> orders;

    public Customer() {}

    public void setOrdersInternal(Set<TailorOrder> orders) {
        this.orders = orders;
    }

    public void setOrders(List<TailorOrder> tailorOrders) {
        this.orders = new HashSet<>(tailorOrders);
    }

    protected Set<TailorOrder> getOrdersInternal() {
        if (this.orders == null) {
            this.orders = new HashSet<>();
        }
        return this.orders;
    }

    public List<TailorOrder> getOrders() {
        List<TailorOrder> sortedTailorOrders = new ArrayList<>(getOrdersInternal());
        PropertyComparator.sort(sortedTailorOrders, new MutableSortDefinition("id", true, true));
        return sortedTailorOrders;
    }

    public void addOrder(TailorOrder order) {
        if (order.isNew()) {
            getOrdersInternal().add(order);
        }
        order.setCustomer(this);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("new", this.isNew())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName())
                .append("middleName", this.getMiddleName())
                .append("email", this.getEmail())
                .append("phone", this.getPhone())
                .toString();
    }
}
