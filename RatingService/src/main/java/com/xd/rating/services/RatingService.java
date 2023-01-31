package com.xd.rating.services;

import com.xd.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating create(Rating rating);

    // get all
    List<Rating> getRatings();

    // get all by User id
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);

}
