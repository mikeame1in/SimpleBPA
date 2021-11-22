package com.amelin.simplecrm.domain.orders.ordertypes;

import com.amelin.simplecrm.domain.NamedEntity;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;

import javax.persistence.*;
import java.util.Set;

@Entity
public class WearDegree extends NamedEntity {

    @OneToMany(mappedBy = "wearDegree", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TailorOrder> orders;

}
