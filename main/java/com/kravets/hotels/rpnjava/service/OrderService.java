package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.OrderEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.data.other.RoomWithFreeRoomsLeft;
import com.kravets.hotels.rpnjava.exception.NoFreeRoomsAvaliableException;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        return orderRepository.getActiveOrders(
                roomEntity,
                checkInDate.minusDays(1), checkInDate, checkInDate.plusDays(1),
                checkOutDate.minusDays(1), checkOutDate, checkOutDate.plusDays(1),
                DateUtils.getCurrentDateTime()
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

    public void createOrder(LocalDate checkInDate, LocalDate checkOutDate, UserEntity userEntity, RoomEntity roomEntity) throws NoFreeRoomsAvaliableException {
        if (roomEntity.getRoomsCount() > getTakenRoomsCount(roomEntity, checkInDate, checkOutDate)) {
            OrderEntity orderEntity = new OrderEntity(checkInDate, checkOutDate, userEntity, roomEntity);
            orderEntity.setCost(ChronoUnit.DAYS.between(checkInDate, checkOutDate) * roomEntity.getCostPerNight());
            if (roomEntity.isPrepaymentRequired()) {
                orderEntity.setExpireDateTime(DateUtils.getCurrentDateTime().plusHours(1));
            }
            orderRepository.save(orderEntity);
        } else {
            throw new NoFreeRoomsAvaliableException();
        }
    }

    public void removeOutdatedOrders() {
        List<OrderEntity> outdatedOrderEntities = orderRepository.findAllByExpireDateTimeBefore(DateUtils.getCurrentDateTime());
        orderRepository.deleteAllInBatch(outdatedOrderEntities);
    }
}
