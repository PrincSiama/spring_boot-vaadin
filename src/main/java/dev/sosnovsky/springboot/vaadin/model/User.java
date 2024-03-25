package dev.sosnovsky.springboot.vaadin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public User(String lastName, String firstName, String patronymic,
                LocalDate dateOfBirth, String email, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(String lastName, String firstName, String patronymic,
                LocalDate dateOfBirth, String email, String phoneNumber, String imageLink) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageLink = imageLink;
    }

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

    @Column(name = "image_link")
    private String imageLink;
}
