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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<User>  findUser(@PathVariable long id) {
        User user = userService.findByIdStub(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/users/" + user.getId())
                .body(user);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user, BindingResult bindingResult) throws BindException {
        log.info("CONTROLLER POST /USERS/");
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        User newUser = userService.save(user);
        return  ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", "/" + "users/" + newUser.getId())
            .body(newUser);
    }

    @PutMapping("/{id}")
    ResponseEntity<User>  updateUser(@PathVariable long id, @RequestBody @Valid User user, BindingResult bindingResult) throws BindException {
        log.info("CONTROLLER PUT /USERS/" + id);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        user.setId(id);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/users/" + user.getId())
                .body(user);
    }

    @PatchMapping("/{id}")
    ResponseEntity<User>  updateUserFields(@PathVariable long id, @RequestBody User user) throws FieldValidationException {
        log.info("CONTROLLER PATCH /USERS/" + id);
        user.setId(id);
        User updatedUser = userService.updateSomeFields(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/users/" + user.getId())
                .body(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable long id) {
        log.info("CONTROLLER DELETE /USERS/" + id);
        userService.delete(id);
    }

}
