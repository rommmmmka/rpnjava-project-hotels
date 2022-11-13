package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    private final SessionRepository sessionRepository;


    @Autowired
    public LogoutService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void deleteSession(SessionEntity sessionEntity) {
        sessionRepository.delete(sessionEntity);
    }
}
