package com.kravets.hotels.rpnjava.misc;

import com.kravets.hotels.rpnjava.entity.SessionEntity;
import com.kravets.hotels.rpnjava.entity.UserEntity;
import com.kravets.hotels.rpnjava.exception.NoAccessException;
import com.kravets.hotels.rpnjava.repository.SessionRepository;
import com.kravets.hotels.rpnjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;


@Component
public final class SessionChecker {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionChecker(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    private SessionEntity addAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("isLoggedIn", false);
        try {
            String sessionKey = WebUtils.getCookie(request, "session_key").getValue();
            Long userId = Long.parseLong(WebUtils.getCookie(request, "user_id").getValue());

            UserEntity userEntity = userRepository.findById(userId).orElse(null);
            if (userEntity == null) {
                return null;
            }
            SessionEntity sessionEntity = sessionRepository.findSessionEntityByUserAndSessionKey(userEntity, sessionKey);
            if (sessionEntity == null) {
                return null;
            }

            sessionEntity.setLastAccessTime(CurrentDate.getDateTime());
            sessionRepository.save(sessionEntity);

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
        if (!(Boolean) model.getAttribute("isAdmin")) {
            throw new NoAccessException();
        }
        return sessionEntity;
    }

    public SessionEntity userAccess(Model model, HttpServletRequest request) throws NoAccessException {
        SessionEntity sessionEntity = addAttributes(model, request);
        if (!(Boolean) model.getAttribute("isLoggedIn")) {
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
        if ((Boolean) model.getAttribute("isLoggedIn")) {
            throw new NoAccessException();
        }
        return sessionEntity;
    }
}
