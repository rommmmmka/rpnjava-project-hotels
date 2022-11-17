package com.kravets.hotels.rpnjava.entity;

import com.kravets.hotels.rpnjava.form.AddRoomForm;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Length(min=6, max=45)
    private String name;

    @Length(max=300)
    private String description;

    @NonNull
    @Length(max=40)
    private String coverPhoto;

    private int guestsLimit;

    private int childrenLimit;

    private int costPerNight;

    private int bedsForOnePersonCount;

    private int bedsForTwoPersonsCount;

    private int roomsNumber;

    private boolean isPrepaymentRequired;

    @ManyToOne
    @NonNull
    private HotelEntity hotel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();


    public RoomEntity() {
    }

    public RoomEntity(AddRoomForm addRoomForm) {
        this.name = addRoomForm.getName();
        this.description = addRoomForm.getDescription();
        this.guestsLimit = addRoomForm.getGuestsLimit();
        this.childrenLimit = addRoomForm.getChildrenLimit();
        this.costPerNight = addRoomForm.getCostPerNight();
        this.bedsForOnePersonCount = addRoomForm.getBedsForOnePersonCount();
        this.bedsForTwoPersonsCount = addRoomForm.getBedsForTwoPersonsCount();
        this.roomsNumber = addRoomForm.getRoomsNumber();
        this.isPrepaymentRequired = addRoomForm.isPrepaymentRequired();
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

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
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

    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public int getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int roomsNumber) {
        this.roomsNumber = roomsNumber;
    }
}
