package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.OrderEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> getAllByRoomAndCheckInDateBetween(RoomEntity roomEntity, Date checkInDateMin, Date checkInDateMax);

    List<OrderEntity> getAllByRoomAndCheckOutDateBetween(RoomEntity roomEntity, Date checkOutDateMin, Date checkOutDateMax);

    List<OrderEntity> getAllByRoomAndCheckInDateIsLessThanAndCheckOutDateIsGreaterThan(RoomEntity roomEntity, Date checkInDateMax, Date checkOutDateMin);
}
