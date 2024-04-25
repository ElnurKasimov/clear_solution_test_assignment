package com.clearsolution.testAssignment.service.impl;


import com.clearsolution.testAssignment.exception.AgeRestrictionException;
import com.clearsolution.testAssignment.exception.BirthDateValidationException;
import com.clearsolution.testAssignment.exception.NullEntityReferenceException;
import com.clearsolution.testAssignment.model.User;
import com.clearsolution.testAssignment.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final Environment environment;

    @Override
    public User save(User user) {
        if (user == null) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(user.getBirthDate(), formatter);
        int minAge = Integer.getInteger(environment.getProperty("minAge"));
        if (parsedDate.isBefore(LocalDate.now())) {
            if (parsedDate.isAfter(LocalDate.now().minusYears(minAge))) {
                throw new AgeRestrictionException("User age must be more than " + minAge + "years old.");
            } else {
                Random random = new Random();
                user.setId(Math.abs(random.nextLong()));
                return user;
            }
        } else {
            throw new BirthDateValidationException("Birth date must be before current date.");
        }

    }

    @Override
    public User updateFields(long id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

//    @Override
//    public User readById(long id) {
//        return userRepository.findById(id).orElseThrow(
//                () -> new EntityNotFoundException("User with id " + id + " not found"));
//    }
//
//    @Override
//    public User update(User user) {
//        if (user != null) {
//            readById(user.getId());
//            return userRepository.save(user);
//        }
//        throw new NullEntityReferenceException("User cannot be 'null'");
//    }
//
//    @Override
//    public void delete(long id) {
//        User user = readById(id);
//        userRepository.delete(user);
//    }
//
//    @Override
//    public List<User> getAll() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not Found!");
//        }
//        return user;
//    }
//
//    @Override
//    public User findByEmail(String username) throws UsernameNotFoundException {
//        return userRepository.findByEmail(username);
//    }

}
