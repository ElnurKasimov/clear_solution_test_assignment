package com.clearsolution.testAssignment.controller;

import com.clearsolution.testAssignment.exception.DateRestrictionException;
import com.clearsolution.testAssignment.exception.FieldValidationException;
import com.clearsolution.testAssignment.exception.NullEntityReferenceException;
import com.clearsolution.testAssignment.model.User;
import com.clearsolution.testAssignment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<Map<String, User>> findUser(@PathVariable long id) {
        User user = userService.findByIdStub(id);
        Map<String, User> responseData = new HashMap<>();
        responseData.put("data", user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/users/" + user.getId())
                .body(responseData);
    }

    @GetMapping("")
    ResponseEntity<Map<String, List<User>>> getUsersInBirthDayRange(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {
        if( from == null || to == null) {
            throw new NullEntityReferenceException("The dates both 'from' and 'to' shouldn't be null.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        if (!from.matches("[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])")) {
            throw new DateRestrictionException("Date 'from' must have format: yyyy-mm-dd with correct values.");
        }
        if (!to.matches("[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])")) {
            throw new DateRestrictionException("Date 'to' must have format: yyyy-mm-dd with correct values.");
        }
        if(toDate.isBefore(fromDate)) {
            throw new DateRestrictionException("Date 'from' must be before 'to'.");
        }
        List<User> users = userService.findUsersInBirthdayRange(from, to);
        Map<String, List<User>> responseData = new HashMap<>();
        responseData.put("data", users);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, User>>  createUser(@RequestBody @Valid User user, BindingResult bindingResult) throws BindException, FieldValidationException, DateRestrictionException {
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
