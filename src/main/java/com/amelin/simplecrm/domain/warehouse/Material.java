package com.amelin.simplecrm.domain.warehouse;

import com.amelin.simplecrm.domain.order.Service;

import javax.persistence.*;

@Entity
public class Material {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;
}
