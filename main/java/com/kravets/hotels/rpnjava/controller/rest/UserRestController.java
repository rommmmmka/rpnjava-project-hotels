package com.kravets.hotels.rpnjava.controller.rest;

import com.kravets.hotels.rpnjava.data.entity.SessionEntity;
import com.kravets.hotels.rpnjava.data.entity.UserEntity;
import com.kravets.hotels.rpnjava.data.form.LoginForm;
import com.kravets.hotels.rpnjava.data.form.RegisterForm;
import com.kravets.hotels.rpnjava.exception.FormValidationException;
import com.kravets.hotels.rpnjava.exception.UserAlreadyExistsException;
import com.kravets.hotels.rpnjava.misc.ResponseStatus;
import com.kravets.hotels.rpnjava.misc.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/api/user/get_info")
    public ResponseEntity<Object> getUserInfo(@RequestParam String sessionKey) {
        try {
            SessionEntity sessionEntity = services.session.getSessionBySessionKey(sessionKey);
            UserEntity userEntity = sessionEntity.getUser();

            Map<String, Object> answer = new HashMap<>();
            answer.put("login", userEntity.getLogin());
            answer.put("shortName", userEntity.getShortName());

            return ResponseStatus.OK.body(answer);
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN.body(null);
        }
    }

    @PostMapping(value = "/api/user/register")
    public ResponseEntity<Object> registerUser(@Valid @ModelAttribute RegisterForm registerForm, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new FormValidationException();
            }

            services.user.registerUser(registerForm);

            return ResponseStatus.OK.body(null);
        } catch (UserAlreadyExistsException e) {
            return ResponseStatus.USER_ALREADY_EXISTS.body(null);
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN.body(null);
        }
    }

    @PostMapping(value = "/api/user/login")
    public ResponseEntity<Object> loginUser(@Valid @ModelAttribute LoginForm loginForm, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new FormValidationException();
            }

            UserEntity userEntity = services.user.loginUser(loginForm);
            SessionEntity sessionEntity = services.session.createSession(userEntity, loginForm.isRememberMe());

            Map<String, Object> answer = new HashMap<>();
            answer.put("sessionKey", sessionEntity.getSessionKey());

            return ResponseStatus.OK.body(answer);
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN.body(null);
        }
    }

    @PostMapping(value = "/api/user/logout")
    public ResponseEntity<Object> logoutUser(@RequestParam String sessionKey) {
        try {
            SessionEntity sessionEntity = services.session.getSessionBySessionKey(sessionKey);
            services.session.removeSession(sessionEntity);

            return ResponseStatus.OK.body(null);
        } catch (Exception e) {
            return ResponseStatus.UNKNOWN.body(null);
        }
    }
}
