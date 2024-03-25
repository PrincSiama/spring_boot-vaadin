package dev.sosnovsky.springboot.vaadin.view;

import com.vaadin.flow.component.UI;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEditUnitTest {
    @Mock
    private UserService userService;
    private UserEditor userEditor;

    @BeforeEach
    void setUp() {
        userEditor = new UserEditor(userService);
        userEditor.setChangeHandler(() -> {
        });
        UI ui = new UI();
        UI.setCurrent(ui);
    }

    @Test
    @DisplayName("Валидация и сохранение корректного пользователя")
    void validateAndSaveTest() {
        User user = new User("Иванов", "Пётр", "Семёнович",
                LocalDate.now().minusYears(60), "ivanov@user.ru", "+77773486137");
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.of(user));
        userEditor.editUser(user);
        userEditor.validateAndSave();

        verify(userService, times(1)).createUser(any(User.class));
    }
}
