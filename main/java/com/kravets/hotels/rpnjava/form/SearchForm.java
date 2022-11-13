package com.kravets.hotels.rpnjava.form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SearchForm {
    private Long city;

    private int adultsNumber;

    private int childrenNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date checkInDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date checkOutDate;

    public SearchForm() {
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public int getAdultsNumber() {
        return adultsNumber;
    }

    public void setAdultsNumber(int adultsNumber) {
        this.adultsNumber = adultsNumber;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
