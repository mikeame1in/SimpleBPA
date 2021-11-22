package com.amelin.simplecrm.dao.repository;

import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TailorOrderRepo extends JpaRepository<TailorOrder, Long> {
    List<TailorOrder> findByCustomerId(Long CustomerId);
}
