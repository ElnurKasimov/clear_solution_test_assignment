package com.clearsolution.testAssignment.service.impl;

import com.clearsolution.testAssignment.exception.AgeRestrictionException;
import com.clearsolution.testAssignment.exception.DateRestrictionException;
import com.clearsolution.testAssignment.exception.FieldValidationException;
import com.clearsolution.testAssignment.exception.NullEntityReferenceException;
import com.clearsolution.testAssignment.model.User;
import com.clearsolution.testAssignment.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void findById() {
    }

    @Test
    @DisplayName("test that save method creates user when input user hasn't id, i.e. id = 0")
    void testThatSaveCreatesUser() {
        User userToCreate = new User( 0, "mask@test.com","John", "Dou",
                "1990-01-01", "Rock County", "(111) 111-1234");
        assertTrue(userService.save(userToCreate).getId() > 0);
    }

    @Test
    @DisplayName("test that save method updates user when input user has id")
    void testThatSaveUpdatesUser() {
        User userToCreate = new User( 5, "mask@test.com","John", "Dou",
                "1990-01-01", "Rock County", "(111) 111-1234");
        assertEquals(5, userService.save(userToCreate).getId());
    }

    @Test
    @DisplayName("test that save method throws NullEntityReferenceException if user in input parameter" +
            " is null")
    void testThatSaveWithNullUserTrowsNullEntityReferenceException() {
        assertThrows(NullEntityReferenceException.class, () -> userService.save(null));
    }

    @Test
    @DisplayName("test that save method throws FieldValidationException if user in input parameter" +
            " has birthday date after current date")
    void testThatSaveWithFutureBirthDateThrowsFieldValidationException() {
        User userToCreate = new User( 0, "mask@test.com","John", "Dou",
                "2025-01-01", "Rock County", "(111) 111-1234");

        assertThrows(FieldValidationException.class, () -> userService.save(userToCreate));
    }

    @Test
    @DisplayName("test that save method throws AgeRestrictionException if user in input parameter" +
            " has age less than 18 years")
    void testThatSaveWithUnderageUserThrowsAgeRestrictionException() {
        User userToCreate = new User( 0, "mask@test.com","John", "Dou",
                "2014-01-01", "Rock County", "(111) 111-1234");
        assertThrows(AgeRestrictionException.class, () -> userService.save(userToCreate));
    }

    //-----------------------------------------------------------------------


//        @Test
//        void testUpdateSomeFieldsSuccess() {
//            // Создаем экземпляр UserService
//            UserService userService = new UserServiceImpl();
//
//            // Создаем пользователя, которого будем обновлять
//            User existingUser = new User();
//            existingUser.setId(1L); // Предполагаем, что пользователь существует
//            existingUser.setFirstName("John");
//
//            // Создаем пользователя с обновленными данными
//            User updatedUser = new User();
//            updatedUser.setLastName("Doe");
//
//            // Подготавливаем findById, чтобы он вернул существующего пользователя
//            when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
//
//            // Вызываем метод обновления и проверяем успешное обновление
//            User resultUser = userService.updateSomeFields(1L, updatedUser);
//            assertEquals("Doe", resultUser.getLastName());
//        }

    @Test
    @DisplayName("test that updateSomeFields method throws NullEntityReferenceException if user in input parameter" +
            " is null")
    void testThatUpdateSomeFieldsWithNullUserTrowsNullEntityReferenceException() {
        assertThrows(NullEntityReferenceException.class, () -> userService.updateSomeFields(1L, null));
    }

    @Test
    @DisplayName("test that updateSomeFields method throws FieldValidationException if user in input parameter" +
            " has email in incorrect format")
    void testThatUpdateSomeFieldsWithInvalidEmailThrowsFieldValidationException() {
        User userToUpdate = new User( 1, "dou_test.com","John", "Dou",
                "2014-01-01", "Rock County", "(111) 111-1234");
        assertThrows(FieldValidationException.class, () -> userService.updateSomeFields(1L, userToUpdate));
    }

    @Test
    @DisplayName("test that updateSomeFields method throws FieldValidationException if user in input parameter" +
            " has birthdate in incorrect format")
    void testThatUpdateSomeFieldsWithInvalidBirthDateThrowsFieldValidationException() {
        User userToUpdate = new User( 1, "dou@test.com","John", "Dou",
                "2014/01/01", "Rock County", "(111) 111-1234");
        assertThrows(FieldValidationException.class, () -> userService.updateSomeFields(1L, userToUpdate));
    }

    @Test
    @DisplayName("test that updateSomeFields method throws DateRestrictionException if user in input parameter" +
            " has birthdate after current date")
    void testThatUpdateSomeFieldsWithFutureBirthDateThrowsDateRestrictionException() {
        User userToUpdate = new User( 1, "dou@test.com","John", "Dou",
                "2025-01-01", "Rock County", "(111) 111-1234");
        assertThrows(DateRestrictionException.class, () -> userService.updateSomeFields(1L, userToUpdate));
    }

    @Test
    @DisplayName("test that updateSomeFields method throws AgeRestrictionException if user in input parameter" +
            " has age less than 18 years")
    void testThatUpdateSomeFieldsWithUnderAgeUserThrowsAgeRestrictionException() {
        User userToUpdate = new User( 1, "dou@test.com","John", "Dou",
                "2025-01-01", "Rock County", "(111) 111-1234");
        assertThrows(AgeRestrictionException.class, () -> userService.updateSomeFields(1L, userToUpdate));
    }






    //-----------------------------------------------------------
    @Test
    void delete() {
    }

    @Test
    void findUsersInBirthdayRange() {
    }
}