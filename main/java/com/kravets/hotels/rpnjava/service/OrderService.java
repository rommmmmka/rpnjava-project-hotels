package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.data.other.RoomEntityWithFreeRoomsField;
import com.kravets.hotels.rpnjava.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public int getTakenRoomsCount(RoomEntity roomEntity, Date checkInDate, Date checkOutDate) {
        ZonedDateTime zonedCheckInDate = DateUtils.convertDateToZonedDate(checkInDate);
        ZonedDateTime zonedCheckOutDate = DateUtils.convertDateToZonedDate(checkOutDate);

        return orderRepository.getAllByRoomAndCheckInDateBetween(
                roomEntity, checkInDate, DateUtils.convertZonedDateToDate(zonedCheckOutDate.minusDays(1))
        ).size() + orderRepository.getAllByRoomAndCheckOutDateBetween(
                roomEntity, DateUtils.convertZonedDateToDate(zonedCheckInDate.plusDays(1)), checkOutDate
        ).size() + orderRepository.getAllByRoomAndCheckInDateIsLessThanAndCheckOutDateIsGreaterThan(
                roomEntity,
                DateUtils.convertZonedDateToDate(zonedCheckInDate.minusDays(1)),
                DateUtils.convertZonedDateToDate(zonedCheckOutDate.plusDays(1))
        ).size();
    }

    public List<RoomEntityWithFreeRoomsField> getFreeRoomsWithFreeRoomsCountFromList(List<RoomEntity> roomEntities, Date checkInDate, Date checkOutDate) {
        List<RoomEntityWithFreeRoomsField> answer = new ArrayList<>();

        for (RoomEntity roomEntity : roomEntities) {
            int takenRoomsCount = getTakenRoomsCount(roomEntity, checkInDate, checkOutDate);
            if (takenRoomsCount < roomEntity.getRoomsNumber()) {
                answer.add(new RoomEntityWithFreeRoomsField(roomEntity, roomEntity.getRoomsNumber() - takenRoomsCount));
            }
        }

        return answer;
    }

    public void createOrder(Date checkInDate, Date checkOutDate, long roomId) {
//        List<RoomEntity> roomEntities =

    }
}
