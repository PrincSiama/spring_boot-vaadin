package dev.sosnovsky.springboot.vaadin.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.security.SecurityService;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
//@Scope("prototype")
@Route("admin")
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
    private final SecurityService securityService;
    private final UserService userService;
    public final Grid<User> grid;
    public Button createUserBtn = new Button("Добавить нового пользователя");
    public final Button logout = new Button("Выйти");
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

        grid.getColumnByKey("id").setHeader("id").setSortProperty("id");
        grid.getColumnByKey("lastName").setHeader("Фамилия");
        grid.getColumnByKey("firstName").setHeader("Имя");
        grid.getColumnByKey("patronymic").setHeader("Отчество");
        grid.getColumnByKey("dateOfBirth").setHeader("Дата рождения");
        grid.getColumnByKey("email").setHeader("Email");
        grid.getColumnByKey("phoneNumber").setHeader("Номер мобильного телефона");
        grid.addComponentColumn(user -> {
            Image image = new Image(user.getImageLink(), "Изображение отсутствует");
            image.setWidth("100px");
            return image;
        }).setHeader("Изображение");


        grid.getColumns().forEach(column -> column.setAutoWidth(true).setResizable(true));

        add(horizontalLayout, grid, userEditor);
        grid.setItems(userService.getUsers());

        grid.asSingleSelect().addValueChangeListener(click -> userEditor.editUser(click.getValue()));

        createUserBtn.addClickListener(click -> userEditor.editUser(new User()));
        logout.addClickListener(click -> securityService.logout());

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            grid.setItems(userService.getUsers());
        });
    }
}
