package com.kravets.hotels.rpnjava.data.form;

import org.springframework.web.multipart.MultipartFile;

public class AddHotelForm {
    private String name;

    private String description;

    private MultipartFile coverPhotoFile;

    private Long city;

    public AddHotelForm() {
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

    public MultipartFile getCoverPhotoFile() {
        return coverPhotoFile;
    }

    public void setCoverPhotoFile(MultipartFile coverPhotoFile) {
        this.coverPhotoFile = coverPhotoFile;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}
