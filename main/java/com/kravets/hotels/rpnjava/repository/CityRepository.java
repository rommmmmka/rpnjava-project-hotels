package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
    List<CityEntity> getAllByDisabled(boolean disabled);
}
