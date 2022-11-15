package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.*;
import com.kravets.hotels.rpnjava.exception.InvalidFilterException;
import com.kravets.hotels.rpnjava.form.*;
import com.kravets.hotels.rpnjava.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Value("${upload.path}")
    String uploadPath;

    @Autowired
    public AdminService(CityRepository cityRepository, HotelRepository hotelRepository, RoomRepository roomRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
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

    public List<HotelEntity> getAllHotels(Long city, String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;

        List<HotelEntity> hotelEntities;
        if (city == 0) {
            hotelEntities = hotelRepository.findAll();
        } else {
            if (getAllEnabledCitiesIds().contains(city)) {
                CityEntity cityEntity = cityRepository.findById(city).orElseThrow();
                hotelEntities = hotelRepository.findHotelEntitiesByCity(cityEntity);
            } else {
                throw new InvalidFilterException();
            }
        }

        switch (property) {
            case "creationDate" -> hotelEntities.sort(Comparator.comparingLong(a -> a.getId() * directionInt));
            case "roomsNumber" -> hotelEntities.sort(Comparator.comparingInt(a -> a.getRooms().size() * directionInt));
            default -> throw new InvalidFilterException();
        }

        return hotelEntities;
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

    public List<RoomEntity> getAllRooms(Long hotel, Long city, String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;

        List<RoomEntity> roomEntities;
        try{
            if (city == 0) {
                if (hotel == 0) {
                    roomEntities = roomRepository.findAll();
                } else {
                    HotelEntity hotelEntity = hotelRepository.findById(hotel).orElseThrow();
                    roomEntities = roomRepository.findRoomEntitiesByHotel(hotelEntity);
                }
            } else {
                CityEntity cityEntity = cityRepository.findById(city).orElseThrow();
                List<HotelEntity> hotelEntitiesByCity = hotelRepository.findHotelEntitiesByCity(cityEntity);
                if (hotel == 0) {
                    roomEntities = roomRepository.findRoomEntitiesByHotelIn(hotelEntitiesByCity);
                } else {
                    HotelEntity hotelEntity = hotelRepository.findById(hotel).orElseThrow();
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

    public List<UserEntity> getAllUsers(String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;
        List<UserEntity> userEntities = userRepository.findAll();

        switch (property) {
            case "registrationDate" -> userEntities.sort(Comparator.comparingLong(a -> a.getId() * directionInt));
            case "ordersCount" -> userEntities.sort(Comparator.comparingInt(a -> a.getOrders().size() * directionInt));
            case "sessionsCount" ->
                    userEntities.sort(Comparator.comparingInt(a -> a.getSessions().size() * directionInt));
            default -> throw new InvalidFilterException();
        }

        return userEntities;
    }

    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    public void editUser(EditUserForm editUserForm) {
        UserEntity userEntity = userRepository.getReferenceById(editUserForm.getId());
        userEntity.setLastName(editUserForm.getLastName());
        userEntity.setFirstName(editUserForm.getFirstName());
        userEntity.setPatronymic(editUserForm.getPatronymic());
        userEntity.setAdmin(editUserForm.isAdmin());
        userRepository.save(userEntity);
    }

    public void killUserSessions(Long id) {
        UserEntity userEntity = userRepository.getReferenceById(id);
        List<SessionEntity> sessions = userEntity.getSessions();
        System.out.println(sessions.size());
        sessionRepository.deleteAllInBatch(sessions);
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
