package com.kravets.hotels.rpnjava.data.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditUserForm {
    private Long id;

    @Size(max = 32)
    @Pattern(regexp = "[A-zА-яЁёІіЎў'\\- ]+")
    private String lastName;

    @Size(max = 32)
    @Pattern(regexp = "[A-zА-яЁёІіЎў'\\- ]+")
    private String firstName;

    @Size(max = 32)
    @Pattern(regexp = "[A-zА-яЁёІіЎў'\\- ]*")
    private String patronymic;

    @Pattern(regexp = "true|false")
    private String isAdmin;


    public EditUserForm() {
    }

    @Override
    public String toString() {
        return "EditUserForm{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                '}';
    }

    public boolean isAdmin() {
        return isAdmin.equals("true");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}
