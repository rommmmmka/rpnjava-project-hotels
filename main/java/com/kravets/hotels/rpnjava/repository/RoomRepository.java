package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.entity.HotelEntity;
import com.kravets.hotels.rpnjava.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findRoomEntitiesByHotelInAndGuestsLimitAndChildrenLimit(
            List<HotelEntity> hotels,
            int guestsLimit,
            int childrenLimit
    );
}
