package dev.sosnovsky.springboot.vaadin.repository;

import dev.sosnovsky.springboot.vaadin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findAllByEmail(String email);
    Optional<User> findAllByPhoneNumber(String phoneNumber);
}
