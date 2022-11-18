package com.kravets.hotels.rpnjava.data.form;

public class EditHotelForm {
    private Long id;

    private String name;

    private String description;


    public EditHotelForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
