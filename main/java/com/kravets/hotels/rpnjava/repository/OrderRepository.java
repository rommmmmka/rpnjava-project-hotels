package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.OrderEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import com.kravets.hotels.rpnjava.data.entity.StatusEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUser(UserEntity userEntity);

    List<OrderEntity> findAllByStatus(StatusEntity statusEntity);

    List<OrderEntity> findAllByUserAndStatus(UserEntity userEntity, StatusEntity statusEntity);

    List<OrderEntity> findAllByExpireDateTimeBefore(LocalDateTime currentDateTime);

    @Query("""
           SELECT o
           FROM OrderEntity o
           WHERE o.room = ?1
             AND (
                    (o.checkInDate >= ?3 AND o.checkInDate <= ?5)
                 OR (o.checkOutDate >= ?4 AND o.checkOutDate <= ?6)
                 OR (o.checkInDate <= ?2 AND o.checkOutDate >= ?7)
                 )
             AND (o.expireDateTime IS NULL OR o.expireDateTime > ?8)
           """)
    List<OrderEntity> getActiveOrders(RoomEntity roomEntity, LocalDate checkInDateM, LocalDate checkInDate, LocalDate checkInDateP, LocalDate checkOutDateM, LocalDate checkOutDate, LocalDate checkOutDateP, LocalDateTime currentDateTime);

}
