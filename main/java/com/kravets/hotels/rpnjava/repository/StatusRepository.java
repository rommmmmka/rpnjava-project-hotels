package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
