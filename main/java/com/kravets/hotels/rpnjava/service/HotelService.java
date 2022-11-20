package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.CityEntity;
import com.kravets.hotels.rpnjava.data.entity.HotelEntity;
import com.kravets.hotels.rpnjava.data.form.AddHotelForm;
import com.kravets.hotels.rpnjava.data.form.EditHotelForm;
import com.kravets.hotels.rpnjava.misc.CoverPhoto;
import com.kravets.hotels.rpnjava.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelEntity getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public HotelEntity getHotelByIdOrElseThrow(Long id) throws NoSuchElementException {
        return hotelRepository.findById(id).orElseThrow();
    }

    public List<HotelEntity> getAllHotels() {
        return hotelRepository.findAll();
    }

    public List<HotelEntity> getHotelsByCity(CityEntity cityEntity) {
        return hotelRepository.getAllByCity(cityEntity);
    }

    public void addHotel(AddHotelForm addHotelForm, CityEntity cityEntity) throws IOException {
        HotelEntity hotelEntity = new HotelEntity(addHotelForm);
        hotelEntity.setCoverPhoto(CoverPhoto.upload(addHotelForm.getCoverPhotoFile()));
        hotelEntity.setCity(cityEntity);

        hotelRepository.save(hotelEntity);
    }

    public void editHotel(EditHotelForm editHotelForm) {
        HotelEntity hotelEntity = getHotelById(editHotelForm.getId());
        hotelEntity.setName(editHotelForm.getName());
        hotelEntity.setDescription(editHotelForm.getDescription());
        hotelRepository.save(hotelEntity);
    }

    public void removeHotel(Long id) {
        HotelEntity hotelEntity = getHotelById(id);
        CoverPhoto.remove(hotelEntity.getCoverPhoto());
        hotelRepository.delete(hotelEntity);
    }

}
