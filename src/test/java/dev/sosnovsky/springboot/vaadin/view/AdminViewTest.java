/*
package dev.sosnovsky.springboot.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.provider.Query;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.security.SecurityService;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminViewTest {

    @Mock
    private SecurityService securityService;
    @Mock
    private UserService userService;
    @Mock
    private UserEditor userEditor;
    private AdminView adminView;

    @BeforeEach
    public void setUp() {
        adminView = new AdminView(userService, userEditor, securityService);
//        adminView = mock(AdminView.class);
    }

    @Test
    @DisplayName("Корректное создание adminView")
    public void setAdminViewTest() {
        doCallRealMethod().when(adminView)

        assertNotNull(adminView);
        assertNotNull(adminView.grid);
        assertNotNull(adminView.createUserBtn);
        assertNotNull(adminView.logout);
    }

    @Test
    @DisplayName("Тестируем кнопку 'Выход'")
    public void logoutButtonTest() {
        adminView.logout.click();
        verify(securityService, times(1)).logout();
    }

    @Test
    @DisplayName("Тестируем кнопку 'Добавить нового пользователя'")
    public void createUserButtonTest() {
        adminView.createUserBtn = mock(Button.class);

        adminView.createUserBtn.click();
        verify(userEditor, times(1)).editUser(any(User.class));
    }

    @Test
    @DisplayName("Количество колонок")
    public void gridColumnsTest() {
        assertNotNull(adminView.grid);
        assertEquals(7, adminView.grid.getColumns().size());
    }

    @Test
    @DisplayName("Количество записей")
    public void testGridItems() {
        List<User> users = Arrays.asList(
                new User("Иванов", "Иван", "Иванович",
                        LocalDate.of(1964, 10, 5), "user1@user.ru",
                        "+79231234567"),
                new User("Петров", "Пётр", "Петрович",
                        LocalDate.of(1987, 6, 15), "user2@user.ru",
                        "+79232345678")
        );
        when(userService.getUsers()).thenReturn(users);

        adminView.grid.setItems(users);

        assertEquals(users.size(), adminView.grid.getDataProvider().size(new Query<>()));
    }

    @Test
    @DisplayName("Выбор существующего пользователя для редактирования")
    public void asSingleSelectUserTest() {
        User selectedUser = new User();
        adminView.grid.asSingleSelect().setValue(selectedUser);
        adminView.createUserBtn.click();
        verify(userEditor, times(1)).editUser(selectedUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Доступ администратора по пути /admin")
    public void testCreateUserButtonClickListener() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/admin")); // Предположим, что adminPage - это представление для администратора
    }

}
*/
