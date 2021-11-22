package com.amelin.simplecrm.domain.orders;

import com.amelin.simplecrm.domain.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "services")
public class Service extends BaseEntity {
    @Column(name = "name")
    private String serviceName;
    private String note;
    private Float price;

//    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private Set<Job> jobs = new HashSet<>();

    public Service() {}

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String name) {
        this.serviceName = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
