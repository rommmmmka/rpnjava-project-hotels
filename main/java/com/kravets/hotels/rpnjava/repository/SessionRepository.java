package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    SessionEntity getBySessionKey(String sessionKey);

    List<SessionEntity> getAllByLastAccessTimeBeforeAndRememberMe(LocalDateTime lastAccessTime, boolean rememberMe);

    List<SessionEntity> getAllByUser(UserEntity userEntity);
}
