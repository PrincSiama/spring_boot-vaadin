package dev.sosnovsky.springboot.vaadin.repository;

import dev.sosnovsky.springboot.vaadin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
