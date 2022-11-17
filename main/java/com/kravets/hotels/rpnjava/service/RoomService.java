package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.entity.HotelEntity;
import com.kravets.hotels.rpnjava.entity.RoomEntity;
import com.kravets.hotels.rpnjava.exception.InvalidFilterException;
import com.kravets.hotels.rpnjava.form.AddRoomForm;
import com.kravets.hotels.rpnjava.form.EditRoomForm;
import com.kravets.hotels.rpnjava.misc.CoverPhoto;
import com.kravets.hotels.rpnjava.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final CitiesService citiesService;
    private final HotelService hotelService;

    @Autowired
    public RoomService(RoomRepository roomRepository, CitiesService citiesService, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.citiesService = citiesService;
        this.hotelService = hotelService;
    }

    public List<RoomEntity> getRoomsByParameters(Long hotel, Long city, String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;

        List<RoomEntity> roomEntities;
        try {
            if (city == 0) {
                if (hotel == 0) {
                    roomEntities = roomRepository.findAll();
                } else {
                    HotelEntity hotelEntity = hotelService.getHotelByIdOrElseThrow(hotel);
                    roomEntities = roomRepository.findRoomEntitiesByHotel(hotelEntity);
                }
            } else {
                CityEntity cityEntity = citiesService.getCityByIdOrElseThrow(city);
                List<HotelEntity> hotelEntitiesByCity = hotelService.getHotelsByCity(cityEntity);
                if (hotel == 0) {
                    roomEntities = roomRepository.findRoomEntitiesByHotelIn(hotelEntitiesByCity);
                } else {
                    HotelEntity hotelEntity = hotelService.getHotelByIdOrElseThrow(hotel);
                    if (hotelEntitiesByCity.contains(hotelEntity)) {
                        roomEntities = roomRepository.findRoomEntitiesByHotel(hotelEntity);
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
            case "roomsNumber" -> roomEntities.sort(Comparator.comparingInt(a -> a.getRoomsNumber() * directionInt));
            default -> throw new InvalidFilterException();
        }

        return roomEntities;
    }

    public List<Long> getRoomsCountListByHotelsList(List<HotelEntity> hotelEntities) {
        List<Long> answer = new ArrayList<>();
        for (HotelEntity hotelEntity : hotelEntities) {
            long currentAnswer = 0;
            for (RoomEntity roomEntity : hotelEntity.getRooms()) {
                currentAnswer += roomEntity.getRoomsNumber();
            }
            answer.add(currentAnswer);
        }
        return answer;
    }

    public void addRoom(AddRoomForm addRoomForm) throws IOException {
        RoomEntity roomEntity = new RoomEntity(addRoomForm);
        roomEntity.setCoverPhoto(CoverPhoto.upload(addRoomForm.getCoverPhotoFile()));
        roomEntity.setHotel(hotelService.getHotelById(addRoomForm.getHotel()));

        roomRepository.save(roomEntity);
    }

    public void editRoom(EditRoomForm editRoomForm) {
        RoomEntity roomEntity = roomRepository.getReferenceById(editRoomForm.getId());
        roomEntity.setName(editRoomForm.getName());
        roomEntity.setDescription(editRoomForm.getDescription());
        roomEntity.setGuestsLimit(editRoomForm.getGuestsLimit());
        roomEntity.setChildrenLimit(editRoomForm.getChildrenLimit());
        roomEntity.setCostPerNight(editRoomForm.getCostPerNight());
        roomEntity.setBedsForOnePersonCount(editRoomForm.getBedsForOnePersonCount());
        roomEntity.setBedsForTwoPersonsCount(editRoomForm.getBedsForTwoPersonsCount());
        roomEntity.setRoomsNumber(editRoomForm.getRoomsNumber());
        roomEntity.setPrepaymentRequired(editRoomForm.isPrepaymentRequired());
        roomRepository.save(roomEntity);
    }

    public void removeRoom(Long id) {
        RoomEntity roomEntity = roomRepository.getReferenceById(id);
        CoverPhoto.remove(roomEntity.getCoverPhoto());
        roomRepository.delete(roomEntity);
    }
}
