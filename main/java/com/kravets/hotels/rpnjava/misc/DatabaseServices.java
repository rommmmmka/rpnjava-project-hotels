package com.kravets.hotels.rpnjava.misc;

import com.kravets.hotels.rpnjava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseServices {
    public final CitiesService cities;
    public final HotelService hotel;
    public final OrderService order;
    public final RoomService room;
    public final SessionService session;
    public final UserService user;

    @Autowired
    public DatabaseServices(
            CitiesService citiesService,
            HotelService hotelService,
            OrderService orderService,
            RoomService roomService,
            SessionService sessionService,
            UserService userService
    ) {
        cities = citiesService;
        hotel = hotelService;
        order = orderService;
        room = roomService;
        session = sessionService;
        user = userService;
    }
}
