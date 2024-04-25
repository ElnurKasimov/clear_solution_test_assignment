package com.clearsolution.testAssignment.controller;

import com.clearsolution.testAssignment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    List<UserResponse> getAllUsers() {
        return userService.getAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    UserResponse getUser(@PathVariable long id) {
        log.info("CONTROLLER GET /API/USERS/" + id);
        return new UserResponse(userService.readById(id));
    }

    @PostMapping("/")
    ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest) {
        log.info("CONTROLLER POST /API/USERS/");
        User newUser = UserTransformer.toEntity(userRequest);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setRole(roleService.findByName(userRequest.getRole().toUpperCase()));
        userService.create(newUser);
    return ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", "/api/users/" + newUser.getId())
            .build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void>  updateUser(@PathVariable long id, @RequestBody UserRequest userRequest) {
        log.info("CONTROLLER PUT /API/USERS/" + id);
        User fromDb = userService.readById(id);
        fromDb.setFirstName(userRequest.getFirstName());
        fromDb.setLastName(userRequest.getLastName());
        fromDb.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        fromDb.setRole(roleService.findByName(userRequest.getRole().toUpperCase()));
        userService.create(fromDb);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/users/" + fromDb.getId())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable long id) {
        log.info("CONTROLLER DELETE /API/USERS/" + id);
        userService.delete(id);
    }


}
