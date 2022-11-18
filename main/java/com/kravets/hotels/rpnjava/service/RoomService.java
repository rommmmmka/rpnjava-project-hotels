package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.CityEntity;
import com.kravets.hotels.rpnjava.data.entity.HotelEntity;
import com.kravets.hotels.rpnjava.data.entity.RoomEntity;
import com.kravets.hotels.rpnjava.data.form.AddRoomForm;
import com.kravets.hotels.rpnjava.data.form.EditRoomForm;
import com.kravets.hotels.rpnjava.data.form.SearchForm;
import com.kravets.hotels.rpnjava.misc.CoverPhoto;
import com.kravets.hotels.rpnjava.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomEntity getRoomByIdOrElseThrow(long id) {
        return roomRepository.findById(id).orElseThrow();
    }

    public List<RoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<RoomEntity> getRoomsByHotel(HotelEntity hotelEntity) {
        return roomRepository.getAllByHotel(hotelEntity);
    }

    public List<RoomEntity> getRoomsByHotelInList(List<HotelEntity> hotelEntities) {
        return roomRepository.getAllByHotelIn(hotelEntities);
    }

    public List<RoomEntity> getRoomsBySearchFormLimits(SearchForm searchForm, CityEntity cityEntity) {
        int guestsLimit = searchForm.getAdultsNumber() + searchForm.getChildrenNumber();
        int adultsLimit = searchForm.getAdultsNumber();

        List<RoomEntity> roomEntities = roomRepository.getAllByGuestsLimitLessThanEqualAndAdultsLimitLessThanEqual(guestsLimit, adultsLimit);
        List<RoomEntity> answer = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntities) {
            if (roomEntity.getHotel().getCity() == cityEntity) {
                answer.add(roomEntity);
            }
        }

        return answer;
    }

    public void addRoom(AddRoomForm addRoomForm, HotelEntity hotelEntity) throws IOException {
        RoomEntity roomEntity = new RoomEntity(addRoomForm);
        roomEntity.setCoverPhoto(CoverPhoto.upload(addRoomForm.getCoverPhotoFile()));
        roomEntity.setHotel(hotelEntity);

        roomRepository.save(roomEntity);
    }

    public void editRoom(EditRoomForm editRoomForm) {
        RoomEntity roomEntity = roomRepository.getReferenceById(editRoomForm.getId());
        roomEntity.setName(editRoomForm.getName());
        roomEntity.setDescription(editRoomForm.getDescription());
        roomEntity.setGuestsLimit(editRoomForm.getAdultsNumber() + editRoomForm.getChildrenNumber());
        roomEntity.setAdultsLimit(editRoomForm.getAdultsNumber());
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
