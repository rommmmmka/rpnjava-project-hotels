package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.misc.CurrentDate;
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
        SessionEntity sessionEntity = new SessionEntity(userEntity, CurrentDate.getDateTime());
        sessionRepository.save(sessionEntity);
        return sessionEntity;
    }

    public SessionEntity getSession(UserEntity userEntity, String sessionKey) {
        return sessionRepository.findSessionEntityByUserAndSessionKey(userEntity, sessionKey);
    }

    public void deleteSession(SessionEntity sessionEntity) {
        sessionRepository.delete(sessionEntity);
    }

    public void updateSession(SessionEntity sessionEntity) {
        sessionEntity.setLastAccessTime(CurrentDate.getDateTime());
        sessionRepository.save(sessionEntity);
    }
}
