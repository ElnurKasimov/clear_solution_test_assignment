package com.clearsolution.testAssignment.service;



import com.clearsolution.testAssignment.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User updateFields(long id);
    User update(User user);
    void delete(long id);
    List<User> getAll();
    User findByEmail(String email);
}
