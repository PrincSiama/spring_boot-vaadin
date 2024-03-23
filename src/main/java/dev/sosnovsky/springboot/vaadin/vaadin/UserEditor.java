package dev.sosnovsky.springboot.vaadin.vaadin;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout implements KeyNotifier {
    private final UserService userService;
    private User user;

    TextField lastName = new TextField("Фамилия");
    TextField firstName = new TextField("Имя");
    TextField patronymic = new TextField("Отчество");
    DatePicker dateOfBirth = new DatePicker("Дата рождения");
    TextField email = new TextField("Email");
    TextField phoneNumber = new TextField("Номер мобильного телефона");

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button cancel = new Button("Отмена");
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<User> binder = new Binder<>(User.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public UserEditor(UserService userService) {
        this.userService = userService;
        add(lastName, firstName, patronymic, dateOfBirth, email, phoneNumber, actions);
        binder.bindInstanceFields(this);

        setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        save.addClickShortcut(Key.ENTER);
        delete.addClickListener(e -> delete());
        delete.addClickShortcut(Key.DELETE);
        cancel.addClickListener(e -> setVisible(false));
        cancel.addClickShortcut(Key.ESCAPE);
        setVisible(false);
    }

    public void save() {
        userService.createUser(user);
        changeHandler.onChange();
    }

    public void delete() {
        userService.deleteUser(user);
        changeHandler.onChange();
    }

    public void editUser(User newUser) {
        if (newUser == null) {
            setVisible(false);
            return;
        }

        if (newUser.getEmail() != null) {
            user = userService.getUserByEmail(newUser.getEmail()).orElse(newUser);
        } else {
            user = newUser;
        }

        binder.setBean(user);
        setVisible(true);
        lastName.focus();
    }
}
