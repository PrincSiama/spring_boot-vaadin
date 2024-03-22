package dev.sosnovsky.springboot.vaadin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private String patronymic;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Email
    private String email;

    @Column(name = "phone_number")
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = "Некорректный формат номера мобильного телефона")
    private String phoneNumber;
}
