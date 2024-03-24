package dev.sosnovsky.springboot.vaadin.service;

import dev.sosnovsky.springboot.vaadin.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);
    void deleteUser(User user);
    List<User> getUsers();
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByPhoneNumber(String phoneNumber);
}
