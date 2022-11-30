package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.HotelEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> getAllByHotel(HotelEntity hotelEntity);

    List<RoomEntity> getAllByHotelIn(List<HotelEntity> hotelEntities);

    List<RoomEntity> getAllByGuestsLimitGreaterThanEqualAndAdultsLimitGreaterThanEqual(int guestsLimitMax, int adultsLimitMax);
}
