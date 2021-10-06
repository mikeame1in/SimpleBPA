package com.amelin.simplecrm.domain.order;

import com.amelin.simplecrm.repos.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String manufacturer;
    private String country;
    private String defects;
    private WearDegree wearDegree;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private List<Service> service;

    public Article() {
    }

    public Article(Map<String, String> allRequestParams, Order order) {
        this.name = allRequestParams.get("name");
        this.manufacturer = allRequestParams.get("manufacturer");
        this.country = allRequestParams.get("country");
        this.defects = allRequestParams.get("defects");

        Integer wearDegree = Integer.parseInt(allRequestParams.get("wearDegree"));
        if (wearDegree == 30)
            this.wearDegree = WearDegree.SLIGHT;
        else if (wearDegree == 50)
            this.wearDegree = WearDegree.INTERMEDIATE;
        else if (wearDegree == 75)
            this.wearDegree = WearDegree.HIGH;

        this.order = order;
    }

    public static Article insert(Map<String, String> allRequestParams, Order order){
        articleRepo.save(new Article(allRequestParams, order));

        return articleRepo.findByOrder_Id(order.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDefects() {
        return defects;
    }

    public void setDefects(String defects) {
        this.defects = defects;
    }

    public WearDegree getWearDegree() {
        return wearDegree;
    }

    public void setWearDegree(WearDegree wearDegree) {
        this.wearDegree = wearDegree;
    }
}
