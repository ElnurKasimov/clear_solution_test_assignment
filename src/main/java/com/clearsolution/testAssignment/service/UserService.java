package com.clearsolution.testAssignment.service;



import com.clearsolution.testAssignment.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User updateSomeFields(User user);
    void delete(long id);
    List<User> findUsersInBirthdayRange(String from, String to);
    User findByIdStub(long id);
}
