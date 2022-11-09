package com.kravets.hotels.rpnjava.form;

import java.time.ZonedDateTime;
import java.util.Date;

public class SearchForm {
    private String city;

    private int adultsNumber;

    private int childrenNumber;

    private Date checkInDate;

    private Date checkOutDate;

    public SearchForm(ZonedDateTime checkInDate) {
        this.setCheckInDate(Date.from(checkInDate.toInstant()));
        this.setCheckOutDate(Date.from(checkInDate.plusDays(1).toInstant()));
        this.setAdultsNumber(1);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
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
