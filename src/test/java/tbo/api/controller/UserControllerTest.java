package tbo.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tbo.api.exception.ResourceNotFoundException;
import tbo.api.service.UserService;
import tn.tbo.demo.api.v1.model.User;
import tn.tbo.demo.api.v1.model.UserWithId;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnUserById() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UserWithId mockUser = new UserWithId();
        mockUser.setLogin("testUser");
        mockUser.setUserId(userId);
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // When & Then
        mockMvc.perform(get("/v1/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(userId.toString()))
                .andExpect(jsonPath("$.login").value("testUser"));
    }


    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        when(userService.getUserById(any())).thenThrow(new ResourceNotFoundException("User not found"));

        // When & Then
        mockMvc.perform(get("/v1/user/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));
    }



    @Test
    void shouldReturnListOfUsers() throws Exception {
        UUID userId = UUID.randomUUID();
        UserWithId userWithId = new UserWithId();
        userWithId.setUserId(userId);
        userWithId.setLogin("John Doe");

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userWithId));

        mockMvc.perform(get("/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user_id").value(userId.toString()))
                .andExpect(jsonPath("$[0].login").value("John Doe"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void shouldCreateUser() throws Exception {
        User user = new User();
        user.setLogin("John Doe");

        doNothing().when(userService).createUser(any(User.class));

        mockMvc.perform(post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"John Doe\"}"))
                .andExpect(status().isCreated());

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setLogin("John Doe");

        doNothing().when(userService).updateUser(any(UUID.class), any(User.class));

        mockMvc.perform(patch("/v1/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).updateUser(any(UUID.class), any(User.class));
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        UUID userId = UUID.randomUUID();

        doNothing().when(userService).deleteUserById(userId);

        mockMvc.perform(delete("/v1/user/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUserById(userId);
    }


}
