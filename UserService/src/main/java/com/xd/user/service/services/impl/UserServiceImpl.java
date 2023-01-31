package com.xd.user.service.services.impl;

import com.xd.user.service.entities.Hotel;
import com.xd.user.service.entities.Rating;
import com.xd.user.service.entities.User;
import com.xd.user.service.external.services.HotelService;
import com.xd.user.service.repo.UserRepository;
import com.xd.user.service.services.UserService;
import com.xd.user.service.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        //generate unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server!" + userId));

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" +user.getUserId(), Rating[].class);
        logger.info("{} ",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //ResponseEntity<Hotel> forObject = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forObject.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            logger.info("response status code: {}",forObject.getStatusCode());
            // api call to hotel service to get the hotel
            // set the hotel to reating
            rating.setHotel(hotel);
            // return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
