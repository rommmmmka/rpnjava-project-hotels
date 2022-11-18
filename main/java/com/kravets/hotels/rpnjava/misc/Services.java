package com.kravets.hotels.rpnjava.misc;

import com.kravets.hotels.rpnjava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services {
    public final CityService cities;
    public final HotelService hotel;
    public final OrderService order;
    public final RoomService room;
    public final SessionService session;
    public final UserService user;
    public final DatabaseService db;

    @Autowired
    public Services(
            CityService cityService,
            HotelService hotelService,
            OrderService orderService,
            RoomService roomService,
            SessionService sessionService,
            UserService userService,
            DatabaseService databaseService
    ) {
        cities = cityService;
        hotel = hotelService;
        order = orderService;
        room = roomService;
        session = sessionService;
        user = userService;
        db = databaseService;
    }
}
