package dev.sosnovsky.springboot.vaadin.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.testbench.unit.SpringUIUnitTest;
import dev.sosnovsky.springboot.vaadin.security.SecurityService;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import dev.sosnovsky.springboot.vaadin.vaadin.AdminView;
import dev.sosnovsky.springboot.vaadin.vaadin.LoginView;
import dev.sosnovsky.springboot.vaadin.vaadin.UserEditor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminViewTest extends SpringUIUnitTest {
//    @Autowired
//    MockMvc mockMvc;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserEditor userEditor;
    @Mock
    private UserDetails userDetails;

    @Autowired
    private AdminView adminView;



/*@Test
    @DisplayName("Корректная аутентификация пользователя")
    void correctAuthTest() {
        MvcResult mvcResult = mockMvc.perform(post("/login")
                .

    }*/




    @Test
    @WithAnonymousUser
    @DisplayName("Доступ по адресу /user неавторизованным пользователем")
    void unauthorizedAccessToUserFromUnauthorizedUser() throws Exception {
        AdminView adminView = new AdminView(userService, userEditor, securityService);
        navigate("user", LoginView.class);

        assertEquals(1, 1);

//mockMvc.perform(get("/user")).andExpect(status().isUnauthorized());

    }
    /*@Test
    @WithMockUser(username = "user1@user.ru", password = "user1", roles = "USER")
    @DisplayName("Доступ по адресу /user с правами USER")
    void authorizedAccessToUserFromUser() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Доступ по адресу /admin с правами USER")
    void unauthorizedAccessToAdminFromUser() {

    }*/



}
