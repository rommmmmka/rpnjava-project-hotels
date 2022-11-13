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
public final class SessionChecker {

    private static SessionEntity addAttributes(Model model,
                                               HttpServletRequest request,
                                               UserService userService,
                                               SessionService sessionService) {
        model.addAttribute("isLoggedIn", false);
        try {
            String sessionKey = WebUtils.getCookie(request, "session_key").getValue();
            Long userId = Long.parseLong(WebUtils.getCookie(request, "user_id").getValue());

            UserEntity userEntity = userService.getUser(userId);
            if (userEntity == null) {
                return null;
            }
            SessionEntity sessionEntity = sessionService.getSession(userEntity, sessionKey);
            if (sessionEntity == null) {
                return null;
            }

            sessionService.updateSession(sessionEntity);

            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", userEntity.isAdmin());
            model.addAttribute("shortName", userEntity.getShortName());

            return sessionEntity;
        } catch (Exception e) {
            return null;
        }
    }

    public static SessionEntity adminAccess(Model model,
                                            HttpServletRequest request,
                                            UserService userService,
                                            SessionService sessionService) throws NoAccessException {
        SessionEntity sessionEntity = addAttributes(model, request, userService, sessionService);
        if (!(Boolean) model.getAttribute("isAdmin")) {
            throw new NoAccessException();
        }

        return sessionEntity;
    }

    public static SessionEntity userAccess(Model model,
                                           HttpServletRequest request,
                                           UserService userService,
                                           SessionService sessionService) throws NoAccessException {
        SessionEntity sessionEntity = addAttributes(model, request, userService, sessionService);
        if (!(Boolean) model.getAttribute("isLoggedIn")) {
            throw new NoAccessException();
        }

        return sessionEntity;
    }

    public static SessionEntity noRestrictionAccess(Model model,
                                                    HttpServletRequest request,
                                                    UserService userService,
                                                    SessionService sessionService) {
        SessionEntity sessionEntity = addAttributes(model, request, userService, sessionService);
        return sessionEntity;
    }

    public static SessionEntity loggedOutAccess(Model model,
                                                HttpServletRequest request,
                                                UserService userService,
                                                SessionService sessionService) throws NoAccessException {
        SessionEntity sessionEntity = addAttributes(model, request, userService, sessionService);
        if ((Boolean) model.getAttribute("isLoggedIn")) {
            throw new NoAccessException();
        }

        return sessionEntity;
    }
}
