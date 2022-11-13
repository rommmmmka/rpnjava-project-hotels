package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.InvalidPasswordException;
import com.kravets.hotels.rpnjava.exception.UserAlreadyExistsException;
import com.kravets.hotels.rpnjava.exception.UserNotFoundException;
import com.kravets.hotels.rpnjava.form.LoginForm;
import com.kravets.hotels.rpnjava.form.RegisterForm;
import com.kravets.hotels.rpnjava.repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static String createHash(String login, String password, String salt) throws NoSuchAlgorithmException {
        String nonHashedPassword = login + '/' + password + '/' + salt;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = messageDigest.digest(nonHashedPassword.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(hashedPassword);
    }

    public void registerUser(RegisterForm registerForm) throws UserAlreadyExistsException, NoSuchAlgorithmException {
        if (userRepository.findUserEntityByLogin(registerForm.getLogin()) != null) {
            throw new UserAlreadyExistsException();
        }

        UserEntity userEntity = new UserEntity(registerForm);

        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        userEntity.setPasswordSalt(Base64.encodeBase64String(salt));

        String hashedPassword = createHash(registerForm.getLogin(), registerForm.getPassword(), userEntity.getPasswordSalt());
        userEntity.setPasswordHash(hashedPassword);

        userRepository.save(userEntity);
    }

    public UserEntity loginUser(LoginForm loginForm) throws UserNotFoundException, NoSuchAlgorithmException, InvalidPasswordException {
        UserEntity userEntity = userRepository.findUserEntityByLogin(loginForm.getLogin());

        if (userEntity == null) {
            throw new UserNotFoundException();
        }

        String hashedPassword = createHash(loginForm.getLogin(), loginForm.getPassword(), userEntity.getPasswordSalt());
        if (!Objects.equals(hashedPassword, userEntity.getPasswordHash())) {
            throw new InvalidPasswordException();
        }

        return userEntity;
    }

    public UserEntity getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.orElse(null);
    }

}
