package dev.sosnovsky.springboot.vaadin.vaadin;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UIScope
@SpringComponent
//@Component
//@Scope("prototype")
public class UserEditor extends VerticalLayout implements KeyNotifier {
    private final UserService userService;
    private User user;

    TextField lastName = new TextField("Фамилия");
    TextField firstName = new TextField("Имя");
    TextField patronymic = new TextField("Отчество");
    DatePicker dateOfBirth = new DatePicker("Дата рождения");
    EmailField email = new EmailField("Email адрес");
    TextField phoneNumber = new TextField("Номер мобильного телефона");
    TextField imageLink = new TextField("Ссылка на изображение");

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button cancel = new Button("Отмена");
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<User> binder = new Binder<>(User.class);
    boolean validatedEmailAndPhoneNumber = true;

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public UserEditor(UserService userService) {
        this.userService = userService;

        // настраиваем поля и правила их валидации
        binderRequiredTextField(lastName, "Фамилия должна быть заполнена", "lastName");
        binderRequiredTextField(firstName, "Имя должно быть заполнено", "firstName");
        binderRequiredTextField(phoneNumber, "Необходимо указать корректный номер мобильного телефона",
                "phoneNumber");

        binder.forField(patronymic)
                .bind("patronymic");

        dateOfBirth.setHelperText("В формате DD.MM.YYYY");
        dateOfBirth.setMax(LocalDate.now());
        binder.forField(dateOfBirth)
                .asRequired("Дата рождения должна быть заполнена")
                .bind("dateOfBirth");

        binder.forField(email)
                .asRequired("Поле email должно быть заполнено")
                .withValidator(new EmailValidator("Необходимо указать корректный email"))
                .bind("email");

        phoneNumber.setPattern("^((\\+7|7|8)+([0-9]){10})$");

        binder.forField(imageLink)
                .bind("imageLink");

        binder.setBean(user);

        add(lastName, firstName, patronymic, dateOfBirth, email, phoneNumber, imageLink, actions);

        setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        // настройка действий при нажатии кнопок
        save.addClickListener(e -> validateAndSave());
        save.addClickShortcut(Key.ENTER);
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));
        cancel.addClickShortcut(Key.ESCAPE);
        setVisible(false);
    }

    // метод служит для валидации сохраняемых пользователей и
    // предотвращения сохранения нескольких пользователей с одинаковыми email и номерами телефона
    public void validateAndSave() {
        if (binder.isValid()) {
            if (!isExistsEmailOrPhoneNumber()) {
                userService.createUser(user);
                changeHandler.onChange();
                Notification.show("Пользователь сохранён");
            } else {
                Notification.show("Пользователь с таким email или номером телефона уже существует");
            }
        } else {
            Notification.show("Невозможно сохранить пользователя. " +
                    "Все обязательные поля должны быть корректно заполнены");
        }
    }

    // перед удалением пользователя убеждаемся, что пользователь с таким email есть в базе данных
    public void delete() {
        if (userService.getUserByEmail(user.getEmail()).isPresent()) {
            userService.deleteUser(userService.getUserByEmail(user.getEmail()).get());
            Notification.show("Пользователь удалён");
            changeHandler.onChange();
        } else {
            Notification.show("Невозможно удалить. Пользователь с такими данными не существует");
        }
    }

    // редактирование существующего или создание нового пользователя
    public void editUser(User newUser) {
        if (newUser == null) {
            setVisible(false);
            return;
        }

        // если у пользователя имеется email, то получаем пользователя из базы для редактирования,
        // запрещая изменение email и номера телефона,
        // а также отключаем необходимость валидации этих полей при сохранении
        if (newUser.getEmail() != null) {
            user = userService.getUserByEmail(newUser.getEmail()).get();
            email.setReadOnly(true);
            phoneNumber.setReadOnly(true);
            validatedEmailAndPhoneNumber = false;

            // если пользователь новый, то разрешаем заполнение всех полей и проводим полную валидацию,
            // включая поиск email и номера телефона в базе данных у существующих пользователей
        } else {
            email.setReadOnly(false);
            phoneNumber.setReadOnly(false);
            validatedEmailAndPhoneNumber = true;
            user = newUser;
        }

        cancel.setVisible(true);
        binder.setBean(user);
        setVisible(true);
        lastName.focus();
    }

    // метод для bind текстовых полей с обязательным заполнением
    private void binderRequiredTextField(TextField field, String message, String propertyName) {
        binder.forField(field)
                .asRequired(message)
                .bind(propertyName);
    }

    // метод для проверки дубликатов вводимых у нового пользователя email и номера телефона
    // в базе данных у существующих пользователей
    private boolean isExistsEmailOrPhoneNumber() {
        if (validatedEmailAndPhoneNumber) {
            return userService.getUserByEmail(user.getEmail()).isPresent() ||
                    userService.getUserByPhoneNumber(user.getPhoneNumber()).isPresent();
        } else {
            validatedEmailAndPhoneNumber = true;
            return false;
        }
    }
}
