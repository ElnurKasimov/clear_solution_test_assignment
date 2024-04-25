package com.clearsolution.testAssignment.controller;

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

//    @GetMapping
//    List<UserResponse> getAllUsers() {
//        return userService.getAll().stream()
//                .map(UserResponse::new)
//                .collect(Collectors.toList());
//    }
//    @GetMapping("/{id}")
//    UserResponse getUser(@PathVariable long id) {
//        log.info("CONTROLLER GET /API/USERS/" + id);
//        return new UserResponse(userService.readById(id));
//    }

    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody @Valid User user, BindingResult bindingResult) throws BindException {
        log.info("CONTROLLER POST /USERS/");
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        User newUser = userService.save(user);
        return  ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", "/" +
                    "users/" + newUser.getId())
            .build();
    }

//    @PutMapping("/{id}")
//    ResponseEntity<Void>  updateUser(@PathVariable long id, @RequestBody UserRequest userRequest) {
//        log.info("CONTROLLER PUT /API/USERS/" + id);
//        User fromDb = userService.readById(id);
//        fromDb.setFirstName(userRequest.getFirstName());
//        fromDb.setLastName(userRequest.getLastName());
//        fromDb.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        fromDb.setRole(roleService.findByName(userRequest.getRole().toUpperCase()));
//        userService.create(fromDb);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .header("Location", "/api/users/" + fromDb.getId())
//                .build();
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    void deleteUser(@PathVariable long id) {
//        log.info("CONTROLLER DELETE /API/USERS/" + id);
//        userService.delete(id);
//    }


}
