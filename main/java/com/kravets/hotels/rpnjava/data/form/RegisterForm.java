package com.kravets.hotels.rpnjava.data.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterForm {
    @Size(min = 6, max = 32)
    @Pattern(regexp = "[A-z0-9\\-_]+")
    private String login;

    @Size(min = 6, max = 32)
    @Pattern(regexp = "[A-z0-9\\-_]+")
    private String password;

    @Size(max = 32)
    @Pattern(regexp = "[A-zА-яЁёІіЎў'\\- ]+")
    private String lastName;

    @Size(max = 32)
    @Pattern(regexp = "[A-zА-яЁёІіЎў'\\- ]+")
    private String firstName;

    @Size(max = 32)
    @Pattern(regexp = "[A-zА-яЁёІіЎў'\\- ]*")
    private String patronymic;


    public RegisterForm() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
