package com.xd.hotel.services;


import com.xd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    // user operations

    //create
    Hotel create(Hotel hotel);

    //get all users
    List<Hotel> getAllHotels();

    //get single user of given User id;

    Hotel getHotel(String hotelId);

    //to
}
