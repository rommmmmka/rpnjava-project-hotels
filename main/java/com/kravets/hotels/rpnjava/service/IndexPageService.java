package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.entity.HotelEntity;
import com.kravets.hotels.rpnjava.entity.RoomEntity;
import com.kravets.hotels.rpnjava.form.SearchForm;
import com.kravets.hotels.rpnjava.repository.CityRepository;
import com.kravets.hotels.rpnjava.repository.HotelRepository;
import com.kravets.hotels.rpnjava.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexPageService {
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;


    @Autowired
    public IndexPageService(CityRepository cityRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    public List<RoomEntity> getEmptyRooms(SearchForm searchForm) {
        return null;
    }

    public List<Long> getAllEnabledCitiesIds() {
        return cityRepository.findCityEntitiesByDisabled(false).stream().map(CityEntity::getId).toList();
    }

    public CityEntity getCityEntityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public List<HotelEntity> getHotelsInCity(CityEntity cityEntity) {
        return hotelRepository.findHotelEntitiesByCity(cityEntity);
    }

    public List<RoomEntity> getEmptyRoomsInHotels(List<HotelEntity> hotels, SearchForm searchForm) {
        List<RoomEntity> rooms = roomRepository.findRoomEntitiesByHotelInAndGuestsLimitAndChildrenLimit(
                hotels,
                searchForm.getAdultsNumber(),
                searchForm.getChildrenNumber()
        );
        return rooms;
    }
}
