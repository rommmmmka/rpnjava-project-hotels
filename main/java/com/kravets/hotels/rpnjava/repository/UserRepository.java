package com.kravets.hotels.rpnjava.repository;

import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByLogin(String login);
}