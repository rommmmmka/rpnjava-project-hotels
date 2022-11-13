package com.kravets.hotels.rpnjava.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int guestsLimit;

    private int childrenLimit;

    private int costPerNight;

    private int area;

    private int bedsForOnePersonCount;

    private int bedsForTwoPersonsCount;

    private boolean isPrepaymentRequired;

    @ManyToOne
    private HotelEntity hotel;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders = new ArrayList<>();


    public RoomEntity() {
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
}