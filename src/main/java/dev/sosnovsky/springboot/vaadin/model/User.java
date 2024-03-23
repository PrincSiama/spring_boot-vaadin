package dev.sosnovsky.springboot.vaadin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users"   /*,
        uniqueConstraints =
                {
                        @UniqueConstraint(columnNames = "id"),
                        @UniqueConstraint(columnNames = "email"),
                        @UniqueConstraint(columnNames = "phone_number")
                }*/
)
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
    private LocalDate dateOfBirth;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
