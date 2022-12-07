package com.kravets.hotels.rpnjava.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kravets.hotels.rpnjava.data.form.RegisterForm;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Length(max = 32)
    private String login;

    @JsonIgnore
    @NonNull
    @Length(max = 44)
    private String passwordHash;

    @JsonIgnore
    @NonNull
    @Length(max = 44)
    private String passwordSalt;

    @NonNull
    @Length(max = 45)
    private String lastName;

    @NonNull
    @Length(max = 45)
    private String firstName;

    @Length(max = 45)
    private String patronymic;

    private boolean isAdmin;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionEntity> sessions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();


    public UserEntity() {
    }

    public UserEntity(RegisterForm registerForm) {
        this.login = registerForm.getLogin();
        this.lastName = registerForm.getLastName();
        this.firstName = registerForm.getFirstName();
        this.patronymic = registerForm.getPatronymic();
    }

    public String getShortName() {
        String name = firstName.charAt(0) + ". ";
        if (patronymic != null && patronymic.length() != 0) {
            name += patronymic.charAt(0) + ". ";
        }
        name += lastName;

        if (name.length() > 24) {
            name = name.substring(0, 22) + "...";
        }
        return name;
    }

    public String getFullName() {
        return lastName + ' ' + firstName + ' ' + patronymic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    @NonNull
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@NonNull String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @NonNull
    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(@NonNull String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
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

    public List<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionEntity> sessions) {
        this.sessions = sessions;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
