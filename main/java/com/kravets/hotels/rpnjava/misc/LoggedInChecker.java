package com.kravets.hotels.rpnjava.misc;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.NoAccessException;
import com.kravets.hotels.rpnjava.service.SessionService;
import com.kravets.hotels.rpnjava.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;


@Component
public final class LoggedInChecker {

    private static void addAttributes(Model model,
                                      HttpServletRequest request,
                                      UserService userService,
                                      SessionService sessionService) {
        String sessionKey;
        long userId;
        model.addAttribute("isLoggedIn", false);

        try {
            sessionKey = WebUtils.getCookie(request, "session_key").getValue();
            userId = Long.parseLong(WebUtils.getCookie(request, "user_id").getValue());
        } catch (Exception e) {
            return;
        }

        UserEntity userEntity = userService.getUser(userId);
        if (userEntity == null) {
            return;
        }
        SessionEntity sessionEntity = sessionService.getSession(userEntity, sessionKey);
        if (sessionEntity == null) {
            return;
        }

        model.addAttribute("isLoggedIn", true);
        model.addAttribute("isAdmin", userEntity.isAdmin());
        model.addAttribute("shortName", userEntity.getShortName());
    }

    public static void adminAccess(Model model,
                                   HttpServletRequest request,
                                   UserService userService,
                                   SessionService sessionService) throws NoAccessException {
        addAttributes(model, request, userService, sessionService);
        if (!(Boolean) model.getAttribute("isAdmin")) {
            throw new NoAccessException();
        }
    }

    public static void userAccess(Model model,
                                  HttpServletRequest request,
                                  UserService userService,
                                  SessionService sessionService) throws NoAccessException {
        addAttributes(model, request, userService, sessionService);
        if (!(Boolean) model.getAttribute("isLoggedIn")) {
            throw new NoAccessException();
        }
    }

    public static void noRestrictionAccess(Model model,
                                           HttpServletRequest request,
                                           UserService userService,
                                           SessionService sessionService) {
        addAttributes(model, request, userService, sessionService);
    }

    public static void loggedOutAccess(Model model,
                                       HttpServletRequest request,
                                       UserService userService,
                                       SessionService sessionService) throws NoAccessException {
        addAttributes(model, request, userService, sessionService);
        if ((Boolean) model.getAttribute("isLoggedIn")) {
            throw new NoAccessException();
        }
    }
}
