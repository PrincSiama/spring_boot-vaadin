package dev.sosnovsky.springboot.vaadin.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.repository.UserRepository;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {
    private final UserService userService;
//    private final UserRepository userRepository;
    private final Grid<User> grid;
    private final Button createUserBtn = new Button("Добавить нового пользователя");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout(createUserBtn);
    private final UserEditor userEditor;

    @Autowired
    public MainView(UserService userService, UserEditor userEditor) {
        this.userService= userService;
        this.userEditor = userEditor;
        this.grid = new Grid<>(User.class);

        grid.setColumns("id", "lastName", "firstName", "patronymic", "dateOfBirth", "email", "phoneNumber");
        grid.setColumnReorderingAllowed(true);

        grid.getColumnByKey("id").setHeader("id").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("lastName").setHeader("Фамилия").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("firstName").setHeader("Имя").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("patronymic").setHeader("Отчество").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("dateOfBirth").setHeader("Дата рождения").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("email").setHeader("Email").setAutoWidth(true).setResizable(true);
        grid.getColumnByKey("phoneNumber").setHeader("Номер мобильного телефона").setAutoWidth(true).setResizable(true);

        add(horizontalLayout, grid, userEditor);
        grid.setItems(userService.getUsers());

        grid.asSingleSelect().addValueChangeListener(e -> userEditor.editUser(e.getValue()));

        createUserBtn.addClickListener(e -> userEditor.editUser(new User()));

        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            grid.setItems(userService.getUsers());
        });
    }
}
