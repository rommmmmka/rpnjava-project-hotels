package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CitiesService {
    private final CityRepository cityRepository;

    @Autowired
    public CitiesService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityEntity getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public CityEntity getCityByIdOrElseThrow(Long id) throws NoSuchElementException {
        return cityRepository.findById(id).orElseThrow();
    }

    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }


    public List<Long> getEnabledCitiesIds() {
        return cityRepository.findCityEntitiesByDisabled(false).stream().map(CityEntity::getId).toList();
    }
}
