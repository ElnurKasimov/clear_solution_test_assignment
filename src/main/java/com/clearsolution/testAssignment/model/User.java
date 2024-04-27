package com.clearsolution.testAssignment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    private long id;

    @Email(message = "Email must be in the format of an e-mail address.")
    @NotEmpty(message = "Email shouldn't be null or empty.")
    private String email;

    @NotEmpty(message = "First name shouldn't be null or empty.")
    private String firstName;

    @NotEmpty(message = "Last name shouldn't be null or empty.")
    private String lastName;

    @Pattern(regexp = "[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])",
            message = "Birth date must have format: yyyy-mm-dd with correct values.")
    @NotEmpty(message = "Birthday shouldn't be null or empty.")
    private String birthDate;

    private String address;

    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getBirthDate(), user.getBirthDate()) && Objects.equals(getAddress(), user.getAddress()) && Objects.equals(getPhoneNumber(), user.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getFirstName(), getLastName(), getBirthDate(), getAddress(), getPhoneNumber());
    }
}
