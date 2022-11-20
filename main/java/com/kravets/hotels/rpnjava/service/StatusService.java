package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.StatusEntity;
import com.kravets.hotels.rpnjava.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }

    public StatusEntity getStatusByIdOrElseThrow(long id) throws NoSuchElementException {
        return statusRepository.findById(id).orElseThrow();
    }
}
