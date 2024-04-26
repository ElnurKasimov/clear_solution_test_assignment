package com.clearsolution.testAssignment.controller;

import com.clearsolution.testAssignment.exception.FieldValidationException;
import com.clearsolution.testAssignment.model.User;
import com.clearsolution.testAssignment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<Map<String, User>>  findUser(@PathVariable long id) {
        User user = userService.findByIdStub(id);
        Map<String, User> responseData = new HashMap<>();
        responseData.put("data", user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/users/" + user.getId())
                .body(responseData);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, User>>  createUser(@RequestBody @Valid User user, BindingResult bindingResult) throws BindException {
        log.info("CONTROLLER POST /USERS/");
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        User newUser = userService.save(user);
        Map<String, User> responseData = new HashMap<>();
        responseData.put("data", newUser);
        return  ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", "/" + "users/" + newUser.getId())
            .body(responseData);
    }

    @PutMapping("/{id}")
    ResponseEntity<Map<String, User>>  updateUser(@PathVariable long id, @RequestBody @Valid User user, BindingResult bindingResult) throws BindException {
        log.info("CONTROLLER PUT /USERS/" + id);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        user.setId(id);
        User updatedUser = userService.save(user);
        Map<String, User> responseData = new HashMap<>();
        responseData.put("data", updatedUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/users/" + user.getId())
                .body(responseData);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Map<String, User>>  updateUserFields(@PathVariable long id, @RequestBody User user) throws FieldValidationException {
        log.info("CONTROLLER PATCH /USERS/" + id);
        user.setId(id);
        User updatedUser = userService.updateSomeFields(user);
        Map<String, User> responseData = new HashMap<>();
        responseData.put("data", updatedUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/users/" + user.getId())
                .body(responseData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable long id) {
        log.info("CONTROLLER DELETE /USERS/" + id);
        userService.delete(id);
    }

}
