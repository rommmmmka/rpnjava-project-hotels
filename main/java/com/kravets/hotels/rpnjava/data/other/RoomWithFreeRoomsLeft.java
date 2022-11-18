package com.kravets.hotels.rpnjava.data.other;

import com.kravets.hotels.rpnjava.data.entity.RoomEntity;

public class RoomWithFreeRoomsLeft {
    private final RoomEntity entity;

    private final int freeRoomsLeft;


    public RoomWithFreeRoomsLeft(RoomEntity roomEntity, int freeRoomsLeft) {
        this.entity = roomEntity;
        this.freeRoomsLeft = freeRoomsLeft;
    }

    public RoomEntity getEntity() {
        return entity;
    }

    public int getFreeRoomsLeft() {
        return freeRoomsLeft;
    }
}
