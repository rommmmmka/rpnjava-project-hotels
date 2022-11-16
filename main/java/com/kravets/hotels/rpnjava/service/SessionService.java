package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.misc.CurrentDate;
import com.kravets.hotels.rpnjava.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;

    @Autowired
    public SessionService(SessionRepository sessionRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    public SessionEntity getSessionByUserAndSessionKey(UserEntity userEntity, String sessionKey) {
        return sessionRepository.findSessionEntityByUserAndSessionKey(userEntity, sessionKey);
    }

    public SessionEntity createSession(UserEntity userEntity) {
        SessionEntity sessionEntity = new SessionEntity(userEntity, CurrentDate.getDateTime());
        sessionRepository.save(sessionEntity);
        return sessionEntity;
    }

    public void editSession(SessionEntity sessionEntity) {
        sessionRepository.save(sessionEntity);
    }

    public void removeSession(SessionEntity sessionEntity) {
        sessionRepository.delete(sessionEntity);
    }

    public void removeSessionsByUserId(Long id) throws NoSuchElementException {
        UserEntity userEntity = userService.getUserByIdOrElseThrow(id);
        List<SessionEntity> sessions = userEntity.getSessions();
        System.out.println(sessions.size());
        sessionRepository.deleteAllInBatch(sessions);
    }
}
