package com.clearsolution.testAssignment.service.impl;

import com.clearsolution.testAssignment.exception.AgeRestrictionException;
import com.clearsolution.testAssignment.exception.DateRestrictionException;
import com.clearsolution.testAssignment.exception.FieldValidationException;
import com.clearsolution.testAssignment.exception.NullEntityReferenceException;
import com.clearsolution.testAssignment.model.User;
import com.clearsolution.testAssignment.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final Environment environment;

    @Override
    public User findByIdStub(long id) {
        return new User(id, "dou@test.com","John", "Dou",
                "1970-01-01", "Rock County", "(111) 111-1234");

    }
    @Override
    public User save(User user) {
        if (user == null) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(user.getBirthDate(), formatter);
        int minAge = Integer.parseInt(environment.getProperty("minAge"));
        if (!parsedDate.isBefore(LocalDate.now())) {
            throw new FieldValidationException("Birth date must be before current date.");
        }
        if (parsedDate.isAfter(LocalDate.now().minusYears(minAge).minusDays(1))) {
            throw new AgeRestrictionException("User age must be more than " + minAge + " years.");
        }
        if (user.getId() == 0) {
            Random random = new Random();
            //taking into account that persistent layer is not required this generation id method is only a stub
            user.setId(Math.abs(random.nextLong()));
        }
        return user;
    }

    @Override
    public User updateSomeFields(User user) {
        if (user == null) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
        User updatedUser = findByIdStub(user.getId());
        String email = user.getEmail();
        if( email != null) {
            if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                updatedUser.setEmail(email);
            } else {
                throw new FieldValidationException("Email must be in the format of an e-mail address.");
            }
        }
        if( user.getFirstName() != null) {
            updatedUser.setFirstName(user.getFirstName());
        }
        if( user.getLastName() != null) {
            updatedUser.setLastName(user.getLastName());
        }
        String birthDay = user.getBirthDate();

        if( birthDay != null) {
            if (birthDay.matches("[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])")) {
                updatedUser.setBirthDate(birthDay);
            } else {
                throw new FieldValidationException("Birth date must have format: yyyy-mm-dd with correct values.");
            }
        }
        if( user.getAddress() != null) {
            updatedUser.setAddress(user.getAddress());
        }
        if( user.getPhoneNumber() != null) {
            updatedUser.setPhoneNumber(user.getPhoneNumber());
        }
        return save(updatedUser);
    }

    @Override
    public void delete(long id) {}


    @Override
    public List<User> findUsersInBirthdayRange(String from, String to) {
        User user1 = new User(1, "dou@test.com","John", "Dou",
                from, "Rock County", "(111) 111-1234");
        User user2 = new User(2, "mask@test.com","Elon", "Mask",
                to, "Rockwell County", "(111) 222-1234");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return users;
    }

}
