package com.kravets.hotels.rpnjava.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Length(max = 40)
    private String sessionKey = UUID.randomUUID().toString();

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessTime;

    private boolean rememberMe;

    @ManyToOne
    @NonNull
    private UserEntity user;


    public SessionEntity() {
    }

    public SessionEntity(@NonNull UserEntity user, @NonNull LocalDateTime lastAccessTime, boolean rememberMe) {
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

    @NonNull
    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(@NonNull String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @NonNull
    public LocalDateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(@NonNull LocalDateTime lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @NonNull
    public UserEntity getUser() {
        return user;
    }

    public void setUser(@NonNull UserEntity user) {
        this.user = user;
    }
}
