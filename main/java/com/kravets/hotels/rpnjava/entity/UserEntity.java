package com.kravets.hotels.rpnjava.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String passwordHash;

    private String passwordSalt;

    private String lastName;

    private String firstName;

    private String patronymic;

    private boolean isAdmin;

    @OneToMany(mappedBy = "userId")
    private List<SessionEntity> sessionsList = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<SessionEntity> getSessionsList() {
        return sessionsList;
    }

    public void setSessionsList(List<SessionEntity> sessionsList) {
        this.sessionsList = sessionsList;
    }

    public String getShortName() {
        String name = firstName.charAt(0) + ". " + patronymic.charAt(0) + ". " + lastName;
        if (name.length() > 24) {
            name = name.substring(0, 22) + "...";
        }
        return name;
    }
}