package com.xd.user.service.services;

import com.xd.user.service.entities.User;
import com.xd.user.service.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    // user operations

    //create
    User saveUser(User user);

    //get all users
    List<User> getAllUsers();

    //get single user of given User id;

    User getUser(String userId);

    //to
}
