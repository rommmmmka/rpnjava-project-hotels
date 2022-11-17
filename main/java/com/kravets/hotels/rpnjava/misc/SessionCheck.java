package com.kravets.hotels.rpnjava.misc;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.NoAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public final class SessionCheck {
    private final Services services;

    @Autowired
    public SessionCheck(Services services) {
        this.services = services;
    }

    private SessionEntity addAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("isLoggedIn", false);
        try {
            String sessionKey = WebUtils.getCookie(request, "session_key").getValue();
            SessionEntity sessionEntity = services.session.getSessionBySessionKey(sessionKey);
            if (sessionEntity == null) {
                return null;
            }

            sessionEntity.setLastAccessTime(CurrentDate.getDateTime());
            services.session.editSession(sessionEntity);

            UserEntity userEntity = sessionEntity.getUser();

            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", userEntity.isAdmin());
            model.addAttribute("shortName", userEntity.getShortName());

            return sessionEntity;
        } catch (Exception e) {
            return null;
        }
    }

    public SessionEntity adminAccess(Model model, HttpServletRequest request) throws NoAccessException {
        SessionEntity sessionEntity = addAttributes(model, request);
        Object attribute = model.getAttribute("isAdmin");
        if (attribute == null || !(Boolean) attribute) {
            throw new NoAccessException();
        }
        return sessionEntity;
    }

    public SessionEntity userAccess(Model model, HttpServletRequest request) throws NoAccessException {
        SessionEntity sessionEntity = addAttributes(model, request);
        Object attribute = model.getAttribute("isLoggedIn");
        if (attribute == null || !(Boolean) attribute) {
            throw new NoAccessException();
        }
        return sessionEntity;
    }

    public SessionEntity noRestrictionAccess(Model model, HttpServletRequest request) {
        SessionEntity sessionEntity = addAttributes(model, request);
        return sessionEntity;
    }

    public SessionEntity loggedOutAccess(Model model, HttpServletRequest request) throws NoAccessException {
        SessionEntity sessionEntity = addAttributes(model, request);
        Object attribute = model.getAttribute("isLoggedIn");
        if (attribute == null || (Boolean) attribute) {
            throw new NoAccessException();
        }
        return sessionEntity;
    }
}
