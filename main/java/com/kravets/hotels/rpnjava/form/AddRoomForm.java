package com.kravets.hotels.rpnjava.form;

import org.springframework.web.multipart.MultipartFile;

public class AddRoomForm {
    private String name;

    private String description;

    private MultipartFile coverPhotoFile;

    private Long hotel;

    private int guestsLimit;

    private int childrenLimit;

    private int costPerNight;

    private int area;

    private int bedsForOnePersonCount;

    private int bedsForTwoPersonsCount;

    private boolean isPrepaymentRequired;

    private int roomsNumber;

    public AddRoomForm() {
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

    public Long getHotel() {
        return hotel;
    }

    public void setHotel(Long hotel) {
        this.hotel = hotel;
    }

    public int getGuestsLimit() {
        return guestsLimit;
    }

    public void setGuestsLimit(int guestsLimit) {
        this.guestsLimit = guestsLimit;
    }

    public int getChildrenLimit() {
        return childrenLimit;
    }

    public void setChildrenLimit(int childrenLimit) {
        this.childrenLimit = childrenLimit;
    }

    public int getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(int costPerNight) {
        this.costPerNight = costPerNight;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getBedsForOnePersonCount() {
        return bedsForOnePersonCount;
    }

    public void setBedsForOnePersonCount(int bedsForOnePersonCount) {
        this.bedsForOnePersonCount = bedsForOnePersonCount;
    }

    public int getBedsForTwoPersonsCount() {
        return bedsForTwoPersonsCount;
    }

    public void setBedsForTwoPersonsCount(int bedsForTwoPersonsCount) {
        this.bedsForTwoPersonsCount = bedsForTwoPersonsCount;
    }

    public boolean isPrepaymentRequired() {
        return isPrepaymentRequired;
    }

    public void setPrepaymentRequired(boolean prepaymentRequired) {
        isPrepaymentRequired = prepaymentRequired;
    }

    public int getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int roomsNumber) {
        this.roomsNumber = roomsNumber;
    }
}
