package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionEntity getSessionBySessionKey(String sessionKey) {
        return sessionRepository.getBySessionKey(sessionKey);
    }

    public SessionEntity createSession(UserEntity userEntity, boolean rememberMe) {
        SessionEntity sessionEntity = new SessionEntity(userEntity, DateUtils.getDateTime(), rememberMe);
        sessionRepository.save(sessionEntity);
        return sessionEntity;
    }

    public void editSession(SessionEntity sessionEntity) {
        sessionRepository.save(sessionEntity);
    }

    public void removeSession(SessionEntity sessionEntity) {
        sessionRepository.delete(sessionEntity);
    }

    public void removeSessionsByUser(UserEntity userEntity) throws NoSuchElementException {
        List<SessionEntity> sessions = sessionRepository.getAllByUser(userEntity);
        sessionRepository.deleteAllInBatch(sessions);
    }

    public void removeOutdatedSessions() {
        List<SessionEntity> sessionEntitiesDoNotRemember = sessionRepository.getAllByLastAccessTimeBeforeAndRememberMe(
                DateUtils.getDateTimeMinusDays(1), false
        );
        List<SessionEntity> sessionEntitiesRemember = sessionRepository.getAllByLastAccessTimeBeforeAndRememberMe(
                DateUtils.getDateTimeMinusDays(7), true
        );
        sessionRepository.deleteAllInBatch(sessionEntitiesDoNotRemember);
        sessionRepository.deleteAllInBatch(sessionEntitiesRemember);
    }
}
