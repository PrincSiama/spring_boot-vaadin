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

@Route("user")
@RolesAllowed("USER")
public class UserView extends VerticalLayout {

    @Autowired
    public UserView(UserService userService, SecurityService securityService) {
        Grid<User> grid = new Grid<>(User.class);

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
        // добавляем общие свойства для всех колонок
        grid.getColumns().forEach(column -> column.setAutoWidth(true).setResizable(true));

        grid.addComponentColumn(user -> {
            Image image = new Image(user.getImageLink(), "Изображение отсутствует");
            image.setWidth("100px");
            return image;
        }).setHeader("Изображение");

        Button logout = new Button("Выйти");
        HorizontalLayout horizontalLayout = new HorizontalLayout(logout);
        add(horizontalLayout, grid);
        // получаем email пользователя из аутентификации
        String email = securityService.getAuthenticatedUser().getUsername();
        // по полученному email находим пользователя
        grid.setItems(userService.getUserByEmail(email).get());

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

        logout.addClickListener(click -> securityService.logout());
    }
}
