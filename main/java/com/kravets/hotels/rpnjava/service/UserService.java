package com.kravets.hotels.rpnjava.service;

import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.InvalidFilterException;
import com.kravets.hotels.rpnjava.exception.InvalidPasswordException;
import com.kravets.hotels.rpnjava.exception.UserAlreadyExistsException;
import com.kravets.hotels.rpnjava.exception.UserNotFoundException;
import com.kravets.hotels.rpnjava.form.EditUserForm;
import com.kravets.hotels.rpnjava.form.LoginForm;
import com.kravets.hotels.rpnjava.form.RegisterForm;
import com.kravets.hotels.rpnjava.misc.PasswordHash;
import com.kravets.hotels.rpnjava.repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getUserByIdOrElseThrow(Long id) throws NoSuchElementException {
        return userRepository.findById(id).orElseThrow();
    }

    public List<UserEntity> getUsersByParameters(String property, String direction) throws InvalidFilterException {
        int directionInt = direction.equals("descending") ? -1 : 1;
        List<UserEntity> userEntities = userRepository.findAll();

        switch (property) {
            case "registrationDate" -> userEntities.sort(Comparator.comparingLong(a -> a.getId() * directionInt));
            case "ordersCount" -> userEntities.sort(Comparator.comparingInt(a -> a.getOrders().size() * directionInt));
            case "sessionsCount" ->
                    userEntities.sort(Comparator.comparingInt(a -> a.getSessions().size() * directionInt));
            default -> throw new InvalidFilterException();
        }

        return userEntities;
    }

    public void editUser(EditUserForm editUserForm) {
        UserEntity userEntity = userRepository.getReferenceById(editUserForm.getId());
        userEntity.setLastName(editUserForm.getLastName());
        userEntity.setFirstName(editUserForm.getFirstName());
        userEntity.setPatronymic(editUserForm.getPatronymic());
        userEntity.setAdmin(editUserForm.isAdmin());
        userRepository.save(userEntity);
    }

    public void removeUser(Long id) {
        userRepository.deleteById(id);
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
