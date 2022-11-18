package com.kravets.hotels.rpnjava.data.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {
    @Size(min = 6, max = 32)
    @Pattern(regexp = "[A-z0-9\\-_]+")
    private String login;

    @Size(min = 6, max = 32)
    @Pattern(regexp = "[A-z0-9\\-_]+")
    private String password;

    private boolean rememberMe;


    public LoginForm() {
    }

    public LoginForm(String login) {
        this.login = login;
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

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
