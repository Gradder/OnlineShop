package com.epam.onlineshop.services.impl;

import com.epam.onlineshop.entities.Role;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.repository.UserRepository;
import com.epam.onlineshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Transactional
    @Override
    public boolean addUser(User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) {
            return false;
        } else {
            userRepository.save(User.builder()
                    .role(Role.USER)
                    .username(username)
                    .firstName(user.getFirstName())
                    .isBlocked(false)
                    .password(bCryptPasswordEncoder.encode(user.getPassword()))
                    .address(user.getAddress())
                    .secondName(user.getSecondName())
                    .phoneNumber(user.getPhoneNumber())
                    .build());
            logger.info("User" + username + "was added!");
            return true;
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());
        updatedUser.setAddress(user.getAddress());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setSecondName(user.getSecondName());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(updatedUser);
        logger.info("User" + user.getUsername() + "was updated!");
    }

    @Override
    public Role getRoleByUsername(String username) {
        return userRepository.findByUsername(username).getRole();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public void changeBlockedStatus(User user) {
        user.setIsBlocked(!user.getIsBlocked());
        userRepository.save(user);
    }
}
