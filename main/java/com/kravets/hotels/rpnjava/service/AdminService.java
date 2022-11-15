package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.entity.HotelEntity;
import com.kravets.hotels.rpnjava.entity.RoomEntity;
import com.kravets.hotels.rpnjava.form.AddHotelForm;
import com.kravets.hotels.rpnjava.form.AddRoomForm;
import com.kravets.hotels.rpnjava.form.EditHotelForm;
import com.kravets.hotels.rpnjava.form.EditRoomForm;
import com.kravets.hotels.rpnjava.repository.CityRepository;
import com.kravets.hotels.rpnjava.repository.HotelRepository;
import com.kravets.hotels.rpnjava.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    @Value("${upload.path}")
    String uploadPath;

    @Autowired
    public AdminService(CityRepository cityRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public CityEntity getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }


    public List<Long> getAllEnabledCitiesIds() {
        return cityRepository.findCityEntitiesByDisabled(false).stream().map(CityEntity::getId).toList();
    }

    public void addHotel(AddHotelForm addHotelForm) throws IOException {
        HotelEntity hotelEntity = new HotelEntity(addHotelForm);
        hotelEntity.setCoverPhoto(uploadPhoto(addHotelForm.getCoverPhotoFile()));
        hotelEntity.setCity(getCityById(addHotelForm.getCity()));

        hotelRepository.save(hotelEntity);
    }

    public List<HotelEntity> getAllHotels() {
        return hotelRepository.findAll();
    }

    public void removeHotel(Long id) {
        HotelEntity hotelEntity = hotelRepository.getReferenceById(id);
        File file = new File(uploadPath + hotelEntity.getCoverPhoto());
        file.delete();
        hotelRepository.delete(hotelEntity);
    }

    public void editHotel(EditHotelForm editHotelForm) {
        HotelEntity hotelEntity = hotelRepository.getReferenceById(editHotelForm.getId());
        hotelEntity.setName(editHotelForm.getName());
        hotelEntity.setDescription(editHotelForm.getDescription());
        hotelRepository.save(hotelEntity);
    }

    public HotelEntity getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public List<RoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }

    public void addRoom(AddRoomForm addRoomForm) throws IOException {
        RoomEntity roomEntity = new RoomEntity(addRoomForm);
        roomEntity.setCoverPhoto(uploadPhoto(addRoomForm.getCoverPhotoFile()));
        roomEntity.setHotel(getHotelById(addRoomForm.getHotel()));

        roomRepository.save(roomEntity);
    }

    public void removeRoom(Long id) {
        RoomEntity roomEntity = roomRepository.getReferenceById(id);
        File file = new File(uploadPath + roomEntity.getCoverPhoto());
        file.delete();
        roomRepository.delete(roomEntity);
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

    public String uploadPhoto(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String newFileName = UUID.randomUUID() + ".jpg";
        file.transferTo(new File(uploadPath + newFileName));

        return newFileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

}
