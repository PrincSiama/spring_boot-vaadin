package dev.sosnovsky.springboot.vaadin.view;

import dev.sosnovsky.springboot.vaadin.security.SecurityService;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import dev.sosnovsky.springboot.vaadin.vaadin.AdminView;
import dev.sosnovsky.springboot.vaadin.vaadin.UserEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminViewIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserEditor userEditor;
    private AdminView adminView;

    @BeforeEach
    public void setUp() {
        adminView = new AdminView(userService, userEditor, securityService);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAdminViewPageAccessForAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("grid"))
                .andExpect(model().attributeExists("createUserBtn"))
                .andExpect(model().attributeExists("logout"))
                .andExpect(model().attributeExists("horizontalLayout"))
                .andExpect(model().attributeExists("userEditor"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAdminViewPageAccessForNonAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }
}
