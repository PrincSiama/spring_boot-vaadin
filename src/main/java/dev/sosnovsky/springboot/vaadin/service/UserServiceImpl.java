package dev.sosnovsky.springboot.vaadin.service;

import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }
}
