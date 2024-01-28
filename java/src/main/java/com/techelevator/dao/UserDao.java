package com.techelevator.dao;

import com.techelevator.model.account.RegisterUserDto;
import com.techelevator.model.account.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(RegisterUserDto user);

    int addItinerary(int userId, int itineraryId);
    void deleteItinerary(int itineraryId);
}
