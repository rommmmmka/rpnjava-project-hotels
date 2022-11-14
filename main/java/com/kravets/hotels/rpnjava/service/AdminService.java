package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.entity.HotelEntity;
import com.kravets.hotels.rpnjava.form.AddHotelForm;
import com.kravets.hotels.rpnjava.form.EditHotelForm;
import com.kravets.hotels.rpnjava.repository.CityRepository;
import com.kravets.hotels.rpnjava.repository.HotelRepository;
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
    @Value("${upload.path}")
    String uploadPath;

    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public AdminService(CityRepository cityRepository, HotelRepository hotelRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
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

    public String uploadPhoto(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String newFileName = UUID.randomUUID() + ".jpg";
        file.transferTo(new File(uploadPath + newFileName));

        return newFileName;
    }

    public HotelEntity addHotel(AddHotelForm addHotelForm) throws IOException {
        HotelEntity hotelEntity = new HotelEntity(addHotelForm);
        hotelEntity.setCoverPhoto(uploadPhoto(addHotelForm.getCoverPhotoFile()));
        hotelEntity.setCity(getCityById(addHotelForm.getCity()));

        hotelRepository.save(hotelEntity);

        return  hotelEntity;
    }

    public List<HotelEntity> getAllHotels() {
        return hotelRepository.findAll();
    }

    public String getUploadPath() {
        return uploadPath;
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
}
