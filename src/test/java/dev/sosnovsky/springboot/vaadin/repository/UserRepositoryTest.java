package dev.sosnovsky.springboot.vaadin.repository;

import dev.sosnovsky.springboot.vaadin.model.User;
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

    @Test
    @DisplayName("Сохранение пользователя и получение по email")
    void saveAndFindUserTest() {
        User testUser1 = new User("Иванов", "Пётр", "Семёнович",
                LocalDate.now().minusYears(60), "ivanov@user.ru", "+77773486137");
        User testUser2 = new User("Жданов", "Вячеслав", "Артёмович",
                LocalDate.now().minusYears(35), "jva@user.ru", "+73189004035");

        userRepository.save(testUser1);
        userRepository.save(testUser2);

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());

        User getUser1 = userRepository.findAllByEmail("ivanov@user.ru").get();

        assertNotNull(getUser1);
        assertEquals(testUser1.getEmail(), getUser1.getEmail());
        assertEquals(testUser1.getPhoneNumber(), getUser1.getPhoneNumber());
    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    void findNotExistsUserTest() {
        Optional<User> getUser = userRepository.findAllByEmail("123@user.ru");
        assertFalse(getUser.isPresent());
    }
}