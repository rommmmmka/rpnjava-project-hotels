package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.InvalidPasswordException;
import com.kravets.hotels.rpnjava.exception.UserNotFoundException;
import com.kravets.hotels.rpnjava.form.LoginForm;
import com.kravets.hotels.rpnjava.misc.CurrentDate;
import com.kravets.hotels.rpnjava.misc.PasswordHash;
import com.kravets.hotels.rpnjava.repository.SessionRepository;
import com.kravets.hotels.rpnjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class LoginService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;


    @Autowired
    public LoginService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public UserEntity loginUser(LoginForm loginForm) throws UserNotFoundException, NoSuchAlgorithmException, InvalidPasswordException {
        UserEntity userEntity = userRepository.findUserEntityByLogin(loginForm.getLogin());
        if (userEntity == null) {
            throw new UserNotFoundException();
        }

        String hashedPassword = PasswordHash.createHash(loginForm.getLogin(), loginForm.getPassword(), userEntity.getPasswordSalt());
        if (!Objects.equals(hashedPassword, userEntity.getPasswordHash())) {
            throw new InvalidPasswordException();
        }

        return userEntity;
    }

    public SessionEntity createSession(UserEntity userEntity) {
        SessionEntity sessionEntity = new SessionEntity(userEntity, CurrentDate.getDateTime());
        sessionRepository.save(sessionEntity);
        return sessionEntity;
    }
}
