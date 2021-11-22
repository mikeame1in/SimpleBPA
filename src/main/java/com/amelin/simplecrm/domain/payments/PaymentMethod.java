package com.amelin.simplecrm.domain.payments;

import com.amelin.simplecrm.domain.NamedEntity;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class PaymentMethod extends NamedEntity {

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Payment> payments;

}
