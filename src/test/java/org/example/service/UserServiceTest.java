package org.example.service;

import org.assertj.core.api.Assertions;
import org.example.dto.User;
import org.example.service.UserService;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private static final User IVAN = User.of(2, "Ivan", "111");
    private static final User PETR = User.of(1, "Petr", "123");

    @BeforeAll
    void init() {
        System.out.println("Before All: ");
    }

    private UserService userService;

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        userService = new UserService();
    }

    @Test
    void userServiceIsEmptyIfNoUserAdded() {
        System.out.println("Test 1: " + this);
        List <User> users = userService.getAll();
        assertTrue(users.isEmpty(), "User list should be empty");
    }

    @Test
    void userSizeIfUserAdded() {
        System.out.println("Test 2: " + this);

        userService.add(PETR);
        userService.add(IVAN);

        var users = userService.getAll();

        assertThat(users).hasSize(2);
    }

    @Test
    void userSuccessIfUserExists() {
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getUsername(),IVAN.getPassword());

        assertThat(maybeUser)
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals(IVAN,user));
    }

    @Test
    void logicFailIfPasswordIsNotCorrect() {
        userService.add(IVAN);

        Optional<User> maybeUser = userService.login(IVAN.getUsername(),"dummy");

        assertTrue(maybeUser.isEmpty());
    }

    @Test
    void logicFailIfUserDoesNotExist() {
        userService.add(IVAN);

        Optional<User> maybeUser = userService.login("dummy", IVAN.getPassword());

        assertTrue(maybeUser.isEmpty());
    }
    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("AfterEach: " + this);
    }

    @AfterAll
    void closeConnectionPool() {
        System.out.println("After all: ");
    }
}
