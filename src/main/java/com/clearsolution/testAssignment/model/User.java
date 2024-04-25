package com.clearsolution.testAssignment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    @Email
    private String email;

    @NotEmpty(message = "firstName shouldn't be null or empty")
    private String firstName;

    @NotEmpty(message = "firstName shouldn't be null or empty")
    private String lastName;

    @Past
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

}
