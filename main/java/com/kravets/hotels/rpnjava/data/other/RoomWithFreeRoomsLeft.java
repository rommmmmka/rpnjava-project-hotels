package com.kravets.hotels.rpnjava.data.other;

import com.kravets.hotels.rpnjava.data.entity.RoomEntity;

public class RoomWithFreeRoomsLeft extends RoomEntity {
    private final int freeRoomsLeft;

    public RoomWithFreeRoomsLeft(RoomEntity roomEntity, int freeRoomsLeft) {
        setId(roomEntity.getId());
        setName(roomEntity.getName());
        setDescription(roomEntity.getDescription());
        setCoverPhoto(roomEntity.getCoverPhoto());
        setGuestsLimit(roomEntity.getGuestsLimit());
        setAdultsLimit(roomEntity.getAdultsLimit());
        setCostPerNight(roomEntity.getCostPerNight());
        setBedsForOnePersonCount(roomEntity.getBedsForOnePersonCount());
        setBedsForTwoPersonsCount(roomEntity.getBedsForTwoPersonsCount());
        setRoomsCount(roomEntity.getRoomsCount());
        setPrepaymentRequired(roomEntity.isPrepaymentRequired());
        setHotel(roomEntity.getHotel());
        setOrders(roomEntity.getOrders());
        this.freeRoomsLeft = freeRoomsLeft;
    }

    public int getFreeRoomsLeft() {
        return freeRoomsLeft;
    }
}
