package com.kravets.hotels.rpnjava.data.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Length(max=40)
    private String sessionKey = UUID.randomUUID().toString();

    @NonNull
    private Date lastAccessTime;

    private boolean rememberMe;

    @ManyToOne
    @NonNull
    private UserEntity user;

    public SessionEntity() {
    }

    public SessionEntity(UserEntity user, Date lastAccessTime, boolean rememberMe) {
        this.user = user;
        this.lastAccessTime = lastAccessTime;
        this.rememberMe = rememberMe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
