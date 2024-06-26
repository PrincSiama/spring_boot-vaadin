package dev.sosnovsky.springboot.vaadin.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.sosnovsky.springboot.vaadin.security.SecurityService;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    // настраиваем отображение страницы /login
    public LoginView(SecurityService securityService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm login = new LoginForm();
        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        add(
                new H1("Сервис для работы со списком пользователей"),
                new H2("с использованием spring boot и vaadin"),
                login
        );
    }
}
