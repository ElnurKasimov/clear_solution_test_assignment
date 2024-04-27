package com.clearsolution.testAssignment.controller;

import com.clearsolution.testAssignment.model.User;
import com.clearsolution.testAssignment.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Test that GET  /users&from=...&to=...  works correctly")
    void testThatGetUsersInBirthDayRangeWorksCorrectly() throws Exception {
        String from = "2020-01-01";
        String to = "2024-01-01";
        User user1 = new User(1, "dou@test.com","John", "Dou",
                from, "Rock County", "(111) 111-1234");
        User user2 = new User(2, "mask@test.com","Elon", "Mask",
                to, "Rockwell County", "(111) 222-1234");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        given(userService.findUsersInBirthdayRange(from, to)).willReturn(users);
        mockMvc.perform(get("/users")
                    .param("from", from).param("to", to)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", Matchers.hasKey("data")))
                .andExpect(jsonPath("$.data", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.data[0].email").value("dou@test.com"))
                .andExpect(jsonPath("$.data[0].firstName").value("John"))
                .andExpect(jsonPath("$.data[0].lastName").value("Dou"))
                .andExpect(jsonPath("$.data[0].birthDate").value(from))
                .andExpect(jsonPath("$.data[1].email").value("mask@test.com"))
                .andExpect(jsonPath("$.data[1].firstName").value("Elon"))
                .andExpect(jsonPath("$.data[1].lastName").value("Mask"))
                .andExpect(jsonPath("$.data[1].birthDate").value(to));
        verify(userService, times(1)).findUsersInBirthdayRange(from, to);

    }
    @Test
    @DisplayName("Test that GET  /users&from=...&to=... throws NullEntityReferenceException when 'from' or 'to' is null")
    void testThatGetUsersInBirthDayRangeThrowsNullEntityReferenceExceptionForNull() throws Exception {
        mockMvc.perform(get("/users")
                        .param("to", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test that GET  /users&from=...&to=... throws NullEntityReferenceException when 'from' or 'to' is empty")
    void testThatGetUsersInBirthDayRangeThrowsNullEntityReferenceExceptionForEmpty() throws Exception {
        mockMvc.perform(get("/users")
                        .param("from", "2020-01-01")
                        .param("to", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test that GET  /users&from=...&to=... throws DateRestrictionException when 'from' has incorrect format")
    void testThatGetUsersInBirthDayRangeThrowsDateRestrictionExceptionForIncorrectFromFormat() throws Exception {
        mockMvc.perform(get("/users")
                        .param("from", "2020/01/01")
                        .param("to", "2024-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test that GET  /users&from=...&to=... throws DateRestrictionException when 'to' has incorrect format")
    void testThatGetUsersInBirthDayRangeThrowsDateRestrictionExceptionForIncorrectToFormat() throws Exception {
        mockMvc.perform(get("/users")
                        .param("from", "2020-01-01")
                        .param("to", "2024/01/01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test that GET  /users&from=...&to=... throws DateRestrictionException when 'from' is after 'to'")
    void testThatGetUsersInBirthDayRangeThrowsDateRestrictionExceptionForFromAfterTo() throws Exception {
        mockMvc.perform(get("/users")
                        .param("from", "2024-01-01")
                        .param("to", "2020-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("Test that POST  /users/  works correctly")
    void testThatPostCreateUserWorksCorrectly() throws Exception {
        User user = new User( 0, "dou@test.com","John", "Dou",
                "1990-01-01", "Rock County", "(111) 111-1234");
        User newUser = new User( 1, "dou@test.com","John", "Dou",
                "1990-01-01", "Rock County", "(111) 111-1234");

        List<User> users = new ArrayList<>();
        users.add(newUser);
        given(userService.save(any(User.class))).willReturn(newUser);
        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", Matchers.hasKey("data")))
//                .andExpect(jsonPath("$", Matchers.hasValue(newUser)))
                .andExpect(jsonPath("$.data.email").value("dou@test.com"))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Dou"))
                .andExpect(jsonPath("$.data.birthDate").value("1990-01-01"))
                .andExpect(jsonPath("$.data.address").value("Rock County"))
                .andExpect(jsonPath("$.data.PhoneNumber").value("(111) 111-1234"))
        ;
        verify(userService, times(1)).save(user);
    }










//
//    @Test
//    void updateUser() {
//    }
//
//    @Test
//    void updateUserFields() {
//    }
//
//    @Test
//    void deleteUser() {
//    }
}