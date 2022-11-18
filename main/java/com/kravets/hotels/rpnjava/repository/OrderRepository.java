package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.OrderEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> getAllByRoomAndCheckInDateBetween(RoomEntity room, LocalDate checkInDate, LocalDate checkInDate2);

    List<OrderEntity> getAllByRoomAndCheckOutDateBetween(RoomEntity room, LocalDate checkOutDate, LocalDate checkOutDate2);

    List<OrderEntity> getAllByRoomAndCheckInDateIsLessThanAndCheckOutDateIsGreaterThan(RoomEntity room, LocalDate checkInDate, LocalDate checkOutDate);
}
