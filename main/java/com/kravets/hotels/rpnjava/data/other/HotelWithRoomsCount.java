package com.kravets.hotels.rpnjava.data.other;

import com.kravets.hotels.rpnjava.data.entity.HotelEntity;

import java.io.Serializable;

public class HotelWithRoomsCount implements Serializable {
    public final HotelEntity entity;

    public final long roomsCount;


    public HotelWithRoomsCount(HotelEntity hotelEntity, long roomsCount) {
        this.entity = hotelEntity;
        this.roomsCount = roomsCount;
    }

    public HotelEntity getEntity() {
        return entity;
    }

    public long getRoomsCount() {
        return roomsCount;
    }
}
