package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    List<HotelEntity> findHotelEntitiesByCity(CityEntity cityEntity);
}
