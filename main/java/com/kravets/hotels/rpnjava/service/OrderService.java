package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.OrderEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import com.kravets.hotels.rpnjava.data.entity.StatusEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.data.form.AddOrderForm;
import com.kravets.hotels.rpnjava.data.other.RoomWithFreeRoomsLeft;
import com.kravets.hotels.rpnjava.exception.NoAccessException;
import com.kravets.hotels.rpnjava.exception.NoFreeRoomsAvaliableException;
import com.kravets.hotels.rpnjava.exception.OrderDoesNotExistException;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity getOrderByIdOrElseThrow(long id) throws NoSuchElementException {
        return orderRepository.findById(id).orElseThrow();
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> getOrdersByUser(UserEntity userEntity) {
        return orderRepository.findAllByUser(userEntity);
    }

    public List<OrderEntity> getOrdersByStatus(StatusEntity statusEntity) {
        return orderRepository.findAllByStatus(statusEntity);
    }

    public List<OrderEntity> getOrdersByUserAndStatus(UserEntity userEntity, StatusEntity statusEntity) {
        return orderRepository.findAllByUserAndStatus(userEntity, statusEntity);
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

    public void createOrder(AddOrderForm addOrderForm, UserEntity userEntity, RoomEntity roomEntity, StatusEntity statusEntity) throws NoFreeRoomsAvaliableException {
        LocalDate checkInDate = addOrderForm.getCheckInDate();
        LocalDate checkOutDate = addOrderForm.getCheckOutDate();
        if (roomEntity.getRoomsCount() > getTakenRoomsCount(roomEntity, checkInDate, checkOutDate)) {
            OrderEntity orderEntity = new OrderEntity(checkInDate, checkOutDate, userEntity, roomEntity, statusEntity);
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

    public void editOrder(long orderId, StatusEntity statusEntity) throws NoSuchElementException {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow();
        orderEntity.setStatus(statusEntity);
        if (statusEntity.getId() == 2) {
            orderEntity.setExpireDateTime(DateUtils.getCurrentDateTime().plusHours(1));
        } else {
            orderEntity.setExpireDateTime(null);
        }
        orderRepository.save(orderEntity);
    }

    public void editOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    public void removeOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }

    public void removeOrderByUser(long orderId, UserEntity userEntity) throws NoAccessException, OrderDoesNotExistException {
        try {
            OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow();
            if (orderEntity.getUser() != userEntity) {
                throw new NoAccessException();
            }
            orderRepository.delete(orderEntity);
        } catch (NoAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new OrderDoesNotExistException();
        }
    }
}
