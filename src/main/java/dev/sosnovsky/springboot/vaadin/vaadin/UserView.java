package dev.sosnovsky.springboot.vaadin.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.security.SecurityService;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("user")
@RolesAllowed("USER")
public class UserView extends VerticalLayout {
    private final SecurityService securityService;
    private final UserService userService;
    private final Grid<User> grid;
    private final Button logout = new Button("Выйти");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout(logout);

    @Autowired
    public UserView(UserService userService, UserEditor userEditor, SecurityService securityService) {
        this.userService= userService;
        this.securityService = securityService;
        this.grid = new Grid<>(User.class);

        grid.setColumns("id", "lastName", "firstName", "patronymic", "dateOfBirth", "email", "phoneNumber");
        grid.setColumnReorderingAllowed(true);

        grid.getColumnByKey("id").setHeader("id").setAutoWidth(true).setResizable(true).setSortProperty("id");
        grid.getColumnByKey("lastName").setHeader("Фамилия").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("firstName").setHeader("Имя").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("patronymic").setHeader("Отчество").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("dateOfBirth").setHeader("Дата рождения").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("email").setHeader("Email").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("phoneNumber").setHeader("Номер мобильного телефона").setAutoWidth(true).setResizable(true);

        add(horizontalLayout, grid);
        String email = securityService.getAuthenticatedUser().getUsername();
        grid.setItems(userService.getUserByEmail(email).get());

        logout.addClickListener(click -> securityService.logout());

        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            grid.setItems(userService.getUsers());
        });
    }
}
