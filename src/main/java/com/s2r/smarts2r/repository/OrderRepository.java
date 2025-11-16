package com.s2r.smarts2r.repository;

import com.s2r.smarts2r.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRetailerId(Long retailerId);
    List<Order> findBySupplierId(Long supplierId);
}
