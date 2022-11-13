package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.CityEntity;
import com.kravets.hotels.rpnjava.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    public List<Long> getAllEnabledCitiesIds() {
        return cityRepository.findCityEntitiesByDisabled(false).stream().map(CityEntity::getId).toList();
    }
}
