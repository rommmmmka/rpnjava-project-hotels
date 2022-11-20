package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
