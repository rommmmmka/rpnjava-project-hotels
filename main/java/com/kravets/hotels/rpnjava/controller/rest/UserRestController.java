package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.data.form.LoginForm;
import com.kravets.hotels.rpnjava.data.form.RegisterForm;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.misc.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserRestController {
    private final Services services;

    @Autowired
    public UserRestController(Services services) {
        this.services = services;
    }

    @PostMapping(value = "/rest/user/register")
    public ResponseEntity<Object> registerUser(@Valid @ModelAttribute RegisterForm registerForm, BindingResult result) {
        Map<String, Object> answer = new HashMap<>();
        try {
            if (result.hasErrors()) {
                throw new FormValidationException();
            }

            services.user.registerUser(registerForm);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/rest/user/login")
    public ResponseEntity<Object> loginUser(@Valid @ModelAttribute LoginForm loginForm, BindingResult result) {
        Map<String, Object> answer = new HashMap<>();
        try {
            if (result.hasErrors()) {
                throw new FormValidationException();
            }

            UserEntity userEntity = services.user.loginUser(loginForm);
            SessionEntity sessionEntity = services.session.createSession(userEntity, loginForm.isRememberMe());

            answer.put("sessionKey", sessionEntity.getSessionKey());
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/rest/user/logout")
    public ResponseEntity<Object> logoutUser(@RequestParam String sessionKey) {
        Map<String, Object> answer = new HashMap<>();
        try {
            SessionEntity sessionEntity = services.session.getSessionBySessionKey(sessionKey);
            services.session.removeSession(sessionEntity);

            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            answer.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }
    }
}
