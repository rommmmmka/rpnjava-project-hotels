package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    SessionEntity findSessionEntityByUserAndSessionKey(UserEntity user, String sessionKey);
}
