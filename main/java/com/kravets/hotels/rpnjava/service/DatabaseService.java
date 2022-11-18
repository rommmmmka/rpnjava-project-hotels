package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.CityEntity;
import com.kravets.hotels.rpnjava.data.entity.HotelEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.data.form.AddHotelForm;
import com.kravets.hotels.rpnjava.data.form.AddRoomForm;
import com.kravets.hotels.rpnjava.data.form.SearchForm;
import com.kravets.hotels.rpnjava.data.other.HotelWithRoomsCount;
import com.kravets.hotels.rpnjava.data.other.RoomWithFreeRoomsLeft;
import com.kravets.hotels.rpnjava.exception.InvalidFilterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DatabaseService {
    private final CityService cityService;
    private final HotelService hotelService;
    private final OrderService orderService;
    private final RoomService roomService;
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public DatabaseService(
            CityService cityService,
            HotelService hotelService,
            OrderService orderService,
            RoomService roomService,
            SessionService sessionService,
            UserService userService
    ) {
        this.cityService = cityService;
        this.hotelService = hotelService;
        this.orderService = orderService;
        this.roomService = roomService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public List<HotelWithRoomsCount> getHotelsWithRoomsCountByParameters(Long city, String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;

        List<HotelEntity> hotelEntities;
        List<HotelWithRoomsCount> answer = new ArrayList<>();
        if (city == 0) {
            hotelEntities = hotelService.getAllHotels();
        } else {
            if (cityService.getEnabledCitiesIds().contains(city)) {
                CityEntity cityEntity = cityService.getCityByIdOrElseThrow(city);
                hotelEntities = hotelService.getHotelsByCity(cityEntity);
            } else {
                throw new InvalidFilterException();
            }
        }

        for (HotelEntity hotelEntity : hotelEntities) {
            long currentRoomsCount = 0;
            for (RoomEntity roomEntity : hotelEntity.getRooms()) {
                currentRoomsCount += roomEntity.getRoomsCount();
            }
            answer.add(new HotelWithRoomsCount(hotelEntity, currentRoomsCount));
        }

        switch (property) {
            case "creationDate" -> answer.sort(Comparator.comparingLong(a -> a.entity.getId() * directionInt));
            case "roomsCount" -> answer.sort(Comparator.comparingLong(a -> a.getRoomsCount() * directionInt));
            default -> throw new InvalidFilterException();
        }

        return answer;
    }

    public List<RoomEntity> getRoomsByParameters(Long hotel, Long city, String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;

        List<RoomEntity> roomEntities;
        try {
            if (city == 0) {
                if (hotel == 0) {
                    roomEntities = roomService.getAllRooms();
                } else {
                    HotelEntity hotelEntity = hotelService.getHotelByIdOrElseThrow(hotel);
                    roomEntities = roomService.getRoomsByHotel(hotelEntity);
                }
            } else {
                CityEntity cityEntity = cityService.getCityByIdOrElseThrow(city);
                List<HotelEntity> hotelEntities = hotelService.getHotelsByCity(cityEntity);
                if (hotel == 0) {
                    roomEntities = roomService.getRoomsByHotelInList(hotelEntities);
                } else {
                    HotelEntity hotelEntity = hotelService.getHotelByIdOrElseThrow(hotel);
                    if (hotelEntities.contains(hotelEntity)) {
                        roomEntities = roomService.getRoomsByHotel(hotelEntity);
                    } else {
                        roomEntities = new ArrayList<>();
                    }
                }
            }
        } catch (Exception e) {
            throw new InvalidFilterException();
        }

        switch (property) {
            case "creationDate" -> roomEntities.sort(Comparator.comparingLong(a -> a.getId() * directionInt));
            case "guestsLimit" -> roomEntities.sort(Comparator.comparingInt(a -> a.getGuestsLimit() * directionInt));
            case "costPerNight" -> roomEntities.sort(Comparator.comparingInt(a -> a.getCostPerNight() * directionInt));
            case "roomsCount" -> roomEntities.sort(Comparator.comparingInt(a -> a.getRoomsCount() * directionInt));
            default -> throw new InvalidFilterException();
        }

        return roomEntities;
    }

    public List<RoomWithFreeRoomsLeft> getEmptyRoomsWithFreeRoomsField(SearchForm searchForm) throws NoSuchElementException {
        List<RoomEntity> roomEntities = roomService.getRoomsBySearchFormLimits(searchForm, cityService.getCityByIdOrElseThrow(searchForm.getCity()));
        return orderService.getFreeRoomsWithFreeRoomsCountFromList(roomEntities, searchForm.getCheckInDate(), searchForm.getCheckOutDate());
    }

    public void addHotel(AddHotelForm addHotelForm) throws IOException {
        hotelService.addHotel(addHotelForm, cityService.getCityById(addHotelForm.getCity()));
    }

    public void addRoom(AddRoomForm addRoomForm) throws IOException {
        roomService.addRoom(addRoomForm, hotelService.getHotelById(addRoomForm.getHotel()));
    }

    public void removeSessionsByUserId(Long id) throws NoSuchElementException {
        UserEntity userEntity = userService.getUserByIdOrElseThrow(id);
        sessionService.removeSessionsByUser(userEntity);
    }

    public boolean checkIfRoomIsEmpty(LocalDate checkInDate, LocalDate checkOutDate, long roomId) {
        RoomEntity roomEntity = roomService.getRoomByIdOrElseThrow(roomId);
        int takenRoomsCount = orderService.getTakenRoomsCount(roomEntity, checkInDate, checkOutDate);

        return takenRoomsCount < roomEntity.getRoomsCount();
    }
}
