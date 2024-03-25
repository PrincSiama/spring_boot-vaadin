package dev.sosnovsky.springboot.vaadin.repository;

import dev.sosnovsky.springboot.vaadin.model.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        testUser1 = new User("Иванов", "Пётр", "Семёнович",
                LocalDate.now().minusYears(60), "ivanov@user.ru", "+77773486137");
        testUser2 = new User("Жданов", "Вячеслав", "Артёмович",
                LocalDate.now().minusYears(35), "jva@user.ru", "+73189004035");

        userRepository.save(testUser1);
        userRepository.save(testUser2);
    }

    @Test
    @DisplayName("Получение пользователя по email")
    void findUserByEmailTest() {
        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());

        User getUser1 = userRepository.findAllByEmail("ivanov@user.ru").get();

        assertNotNull(getUser1);
        assertEquals(testUser1.getEmail(), getUser1.getEmail());
        assertEquals(testUser1.getPhoneNumber(), getUser1.getPhoneNumber());
    }

    @Test
    @DisplayName("Получение несуществующего пользователя по email")
    void findNotExistsUserByEmailTest() {
        Optional<User> getUser = userRepository.findAllByEmail("123@user.ru");
        assertFalse(getUser.isPresent());
    }

    @Test
    @DisplayName("Получение пользователя по номеру телефона")
    void findUserByPhoneNumberTest() {
        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());

        User getUser2 = userRepository.findAllByPhoneNumber("+73189004035").get();

        assertNotNull(getUser2);
        assertEquals(testUser2.getEmail(), getUser2.getEmail());
        assertEquals(testUser2.getPhoneNumber(), getUser2.getPhoneNumber());
    }

    @Test
    @DisplayName("Получение несуществующего пользователя по номеру телефона")
    void findNotExistsUserByPhoneNumberTest() {
        Optional<User> getUser = userRepository.findAllByPhoneNumber("911");
        assertFalse(getUser.isPresent());
    }

}