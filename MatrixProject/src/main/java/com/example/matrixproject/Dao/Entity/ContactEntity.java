package com.example.matrixproject.Dao.Entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="contact")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Please fill Name")
    @Size(min = 2, max = 50, message = "Name size must be between 2 and 50")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Please fill Last Name")
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Please fill Email")
    @Email(message = "Please fill in valid Email")
    @Column(name = "email")
    private String email;

    public ContactEntity(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
