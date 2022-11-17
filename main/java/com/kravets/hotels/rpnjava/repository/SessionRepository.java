package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    SessionEntity findSessionEntityBySessionKey(String sessionKey);

    List<SessionEntity> findSessionEntitiesByLastAccessTimeBeforeAndRememberMe(Date lastAccessTimeMax, boolean rememberMe);
}
