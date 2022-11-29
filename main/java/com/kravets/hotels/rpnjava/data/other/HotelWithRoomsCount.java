package com.kravets.hotels.rpnjava.data.other;

import com.kravets.hotels.rpnjava.data.entity.HotelEntity;


public class HotelWithRoomsCount extends HotelEntity {
    public final long roomsCount;


    public HotelWithRoomsCount(HotelEntity hotelEntity, long roomsCount) {
        setId(hotelEntity.getId());
        setName(hotelEntity.getName());
        setDescription(hotelEntity.getDescription());
        setCoverPhoto(hotelEntity.getCoverPhoto());
        setCity(hotelEntity.getCity());
        setRooms(hotelEntity.getRooms());
        this.roomsCount = roomsCount;
    }

    public long getRoomsCount() {
        return roomsCount;
    }
}
