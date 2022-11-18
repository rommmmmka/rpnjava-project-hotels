package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import com.kravets.hotels.rpnjava.data.other.RoomWithFreeRoomsLeft;
import com.kravets.hotels.rpnjava.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public int getTakenRoomsCount(RoomEntity roomEntity, LocalDate checkInDate, LocalDate checkOutDate) {
        return orderRepository.getAllByRoomAndCheckInDateBetween(roomEntity, checkInDate, checkOutDate.minusDays(1)).size() +
                orderRepository.getAllByRoomAndCheckOutDateBetween(roomEntity, checkInDate.plusDays(1), checkOutDate).size() +
                orderRepository.getAllByRoomAndCheckInDateIsLessThanAndCheckOutDateIsGreaterThan(
                        roomEntity, checkInDate.minusDays(1), checkOutDate.plusDays(1)
                ).size();
    }

    public List<RoomWithFreeRoomsLeft> getFreeRoomsWithFreeRoomsCountFromList(List<RoomEntity> roomEntities, LocalDate checkInDate, LocalDate checkOutDate) {
        List<RoomWithFreeRoomsLeft> answer = new ArrayList<>();

        for (RoomEntity roomEntity : roomEntities) {
            int takenRoomsCount = getTakenRoomsCount(roomEntity, checkInDate, checkOutDate);
            if (takenRoomsCount < roomEntity.getRoomsCount()) {
                answer.add(new RoomWithFreeRoomsLeft(roomEntity, roomEntity.getRoomsCount() - takenRoomsCount));
            }
        }

        return answer;
    }

    public void createOrder(LocalDate checkInDate, LocalDate checkOutDate, long roomId) {
//        List<RoomEntity> roomEntities =

    }
}
