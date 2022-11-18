package com.kravets.hotels.rpnjava.data.form;

import org.springframework.web.multipart.MultipartFile;

public class AddRoomForm {
    private String name;

    private String description;

    private MultipartFile coverPhotoFile;

    private Long hotel;

    private int adultsCount;

    private int childrenCount;

    private int bedsForOnePersonCount;

    private int bedsForTwoPersonsCount;

    private int costPerNight;

    private int roomsCount;

    private String isPrepaymentRequired;


    public AddRoomForm() {
    }

    public boolean isPrepaymentRequired() {
        return isPrepaymentRequired.equals("true");
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

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public int getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(int costPerNight) {
        this.costPerNight = costPerNight;
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

    public String getIsPrepaymentRequired() {
        return isPrepaymentRequired;
    }

    public void setIsPrepaymentRequired(String isPrepaymentRequired) {
        this.isPrepaymentRequired = isPrepaymentRequired;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }
}
