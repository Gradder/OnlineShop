package com.epam.onlineshop.services;

import com.epam.onlineshop.entities.Role;
import com.epam.onlineshop.entities.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    User findByUsername(String username);

    void updateUser(User user);

    List<User> getAllUsers();

    void changeBlockedStatus(User user);

    User findById(Long id);
}
