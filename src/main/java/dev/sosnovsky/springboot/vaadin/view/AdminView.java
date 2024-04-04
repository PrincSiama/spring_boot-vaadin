package dev.sosnovsky.springboot.vaadin.view;

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

@Route("admin")
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
    private final Grid<User> grid;

    @Autowired
    public AdminView(UserService userService, UserEditor userEditor, SecurityService securityService) {
        this.grid = new Grid<>(User.class);

        // задаём порядок расположения колонок
        grid.setColumns("id", "lastName", "firstName", "patronymic", "dateOfBirth", "email", "phoneNumber");
        grid.setColumnReorderingAllowed(true);

        // задаём отображаемые на странице заголовки
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

        // добавляем общие свойства для всех колонок
        grid.getColumns().forEach(column -> column.setAutoWidth(true).setResizable(true));

        Button createUserBtn = new Button("Добавить нового пользователя");
        Button logout = new Button("Выйти");
        HorizontalLayout horizontalLayout = new HorizontalLayout(createUserBtn, logout);
        add(horizontalLayout, grid, userEditor);
        grid.setItems(userService.getUsers());

        // определяем действие для редактирования выбранного из списка пользователя
        grid.asSingleSelect().addValueChangeListener(click -> userEditor.editUser(click.getValue()));

        // определяем действие на нажатие кнопки "Добавить нового пользователя"
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
