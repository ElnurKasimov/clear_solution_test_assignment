package com.clearsolution.testAssignment.service;



import com.clearsolution.testAssignment.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User updateSomeFields(User user);
    User update(User user);
    void delete(long id);
    List<User> findUsersInBirthdayRange(String from, String to);
    List<User> getAll();
    User findById(long id);
}
