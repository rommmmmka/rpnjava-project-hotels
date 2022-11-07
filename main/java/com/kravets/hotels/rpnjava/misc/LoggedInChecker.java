package com.kravets.hotels.rpnjava.misc;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.NoAccessException;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;


@Component
public final class LoggedInChecker {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public LoggedInChecker(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    private Model addAttributes(Model model, HttpServletRequest request) {
        String sessionKey;
        Long userId;
        model.addAttribute("loggedIn", false);

        try {
            sessionKey = WebUtils.getCookie(request, "session_key").getValue();
            userId = Long.parseLong(WebUtils.getCookie(request, "user_id").getValue());
        } catch (Exception e) {
            return model;
        }

        UserEntity userEntity = userService.getUser(userId);
        if (userEntity == null) {
            return model;
        }
        SessionEntity sessionEntity = sessionService.getSession(userEntity, sessionKey);
        if (sessionEntity == null) {
            return model;
        }

        model.addAttribute("isLoggedIn", true);
        model.addAttribute("isAdmin", userEntity.isAdmin());
        model.addAttribute("shortName", userEntity.getShortName());
        return model;
    }

    public Model adminAccess(Model model, HttpServletRequest request) throws NoAccessException {
        model = this.addAttributes(model, request);
        if (!(Boolean) model.getAttribute("isAdmin")) {
            throw new NoAccessException();
        }
        return model;
    }

    public Model userAccess(Model model, HttpServletRequest request) throws NoAccessException {
        model = this.addAttributes(model, request);
        if (!(Boolean) model.getAttribute("loggedIn")) {
            throw new NoAccessException();
        }
        return model;
    }

    public Model noRestrictionAccess(Model model, HttpServletRequest request) {
        return this.addAttributes(model, request);
    }

    public Model loggedOutAccess(Model model, HttpServletRequest request) throws NoAccessException {
        model = this.addAttributes(model, request);
        if ((Boolean) model.getAttribute("loggedIn")) {
            throw new NoAccessException();
        }
        return model;
    }
}
