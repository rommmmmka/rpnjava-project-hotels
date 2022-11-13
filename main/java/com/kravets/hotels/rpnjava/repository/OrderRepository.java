package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
