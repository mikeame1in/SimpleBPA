package com.amelin.simplecrm.dao.repository;

import com.amelin.simplecrm.domain.orders.ordertypes.DryCleaningOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DryCleaningOrderRepo extends JpaRepository<DryCleaningOrder, Long> {
    List<DryCleaningOrder> findByCustomerId(Long CustomerId);
}
