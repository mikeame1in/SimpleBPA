package com.amelin.simplecrm.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Article extends NamedEntity {

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "country")
    private String country;

    @Column(name = "defects")
    private String defects;

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
}
