package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.entity.HotelEntity;
import com.kravets.hotels.rpnjava.exception.InvalidFilterException;
import com.kravets.hotels.rpnjava.form.AddHotelForm;
import com.kravets.hotels.rpnjava.form.EditHotelForm;
import com.kravets.hotels.rpnjava.misc.CoverPhoto;
import com.kravets.hotels.rpnjava.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final CitiesService citiesService;

    @Autowired
    public HotelService(HotelRepository hotelRepository, CitiesService citiesService) {
        this.hotelRepository = hotelRepository;
        this.citiesService = citiesService;
    }

    public HotelEntity getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public HotelEntity getHotelByIdOrElseThrow(Long id) {
        return hotelRepository.findById(id).orElseThrow();
    }

    public List<HotelEntity> getAllHotels() {
        return hotelRepository.findAll();
    }

    public List<HotelEntity> getHotelsByCity(CityEntity cityEntity) {
        return hotelRepository.findHotelEntitiesByCity(cityEntity);
    }

    public List<HotelEntity> getHotelsByParameters(Long city, String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;

        List<HotelEntity> hotelEntities;
        if (city == 0) {
            hotelEntities = hotelRepository.findAll();
        } else {
            if (citiesService.getEnabledCitiesIds().contains(city)) {
                CityEntity cityEntity = citiesService.getCityByIdOrElseThrow(city);
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

    public void addHotel(AddHotelForm addHotelForm) throws IOException {
        HotelEntity hotelEntity = new HotelEntity(addHotelForm);
        hotelEntity.setCoverPhoto(CoverPhoto.upload(addHotelForm.getCoverPhotoFile()));
        hotelEntity.setCity(citiesService.getCityById(addHotelForm.getCity()));

        hotelRepository.save(hotelEntity);
    }

    public void editHotel(EditHotelForm editHotelForm) {
        HotelEntity hotelEntity = hotelRepository.getReferenceById(editHotelForm.getId());
        hotelEntity.setName(editHotelForm.getName());
        hotelEntity.setDescription(editHotelForm.getDescription());
        hotelRepository.save(hotelEntity);
    }

    public void removeHotel(Long id) {
        HotelEntity hotelEntity = hotelRepository.getReferenceById(id);
        CoverPhoto.remove(hotelEntity.getCoverPhoto());
        hotelRepository.delete(hotelEntity);
    }

}
