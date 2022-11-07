package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionEntity createSession(UserEntity userEntity) {
        SessionEntity sessionEntity = new SessionEntity(userEntity);
        sessionRepository.save(sessionEntity);
        return sessionEntity;
    }

    public SessionEntity getSession(UserEntity userId, String sessionKey) {
        return sessionRepository.findByUserIdAndSessionKey(userId, sessionKey);
    }
}
