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

@Route("admin")
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
    private final SecurityService securityService;
    private final UserService userService;
    private final Grid<User> grid;
    private final Button createUserBtn = new Button("Добавить нового пользователя");
    private final Button logout = new Button("Выйти");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout(createUserBtn, logout);
    private final UserEditor userEditor;

    @Autowired
    public AdminView(UserService userService, UserEditor userEditor, SecurityService securityService) {
        this.userService= userService;
        this.userEditor = userEditor;
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

        add(horizontalLayout, grid, userEditor);
        grid.setItems(userService.getUsers());

        grid.asSingleSelect().addValueChangeListener(click -> userEditor.editUser(click.getValue()));

        createUserBtn.addClickListener(click -> userEditor.editUser(new User()));
        logout.addClickListener(click -> securityService.logout());

        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            grid.setItems(userService.getUsers());
        });
    }
}
