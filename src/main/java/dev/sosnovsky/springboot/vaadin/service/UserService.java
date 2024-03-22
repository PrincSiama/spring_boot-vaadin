package dev.sosnovsky.springboot.vaadin.service;

import dev.sosnovsky.springboot.vaadin.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User editUser(User user);
    void deleteUser(User user);
    List<User> getUsers();
}
