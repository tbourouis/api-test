package tbo.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import tbo.api.repository.UserRepository;
import tn.tbo.demo.api.v1.model.User;
import tn.tbo.demo.api.v1.model.UserWithId;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUser() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password123");
        user.setType(User.TypeEnum.USER);

        userService.createUser(user);

        List<UserWithId> users = userService.getAllUsers();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getLogin()).isEqualTo("testUser");
    }

    @Test
    void shouldFindUserById() {
        User user = new User();
        user.setLogin("testUser2");
        user.setPassword("password123");
        user.setType(User.TypeEnum.USER);

        userService.createUser(user);
        UserWithId createdUser = userService.getAllUsers().get(0);

        UserWithId foundUser = userService.getUserById(createdUser.getUserId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getLogin()).isEqualTo("testUser2");
    }

    @Test
    void shouldDeleteUser() {
        User user = new User();
        user.setLogin("testUser3");
        user.setPassword("password123");
        user.setType(User.TypeEnum.USER);

        userService.createUser(user);
        UserWithId createdUser = userService.getAllUsers().get(0);
        UUID userId = createdUser.getUserId();

        userService.deleteUserById(userId);
        assertThrows(Exception.class, () -> userService.getUserById(userId));
    }
}
