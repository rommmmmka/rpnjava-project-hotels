package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.UserAlreadyExistsException;
import com.kravets.hotels.rpnjava.form.RegisterForm;
import com.kravets.hotels.rpnjava.misc.PasswordHash;
import com.kravets.hotels.rpnjava.repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class RegisterService {
    private final UserRepository userRepository;


    @Autowired
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        String hashedPassword = PasswordHash.createHash(
                registerForm.getLogin(),
                registerForm.getPassword(),
                userEntity.getPasswordSalt()
        );
        userEntity.setPasswordHash(hashedPassword);

        userRepository.save(userEntity);
    }
}
