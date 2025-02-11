package tbo.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tbo.api.service.UserService;
import tn.tbo.demo.api.v1.model.User;
import tn.tbo.demo.api.v1.model.UserWithId;
import tn.tbo.demo.api.v1.rest.V1Api;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor

public class UserController implements V1Api {

    private UserService userService;

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserWithId> getById(UUID id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<UserWithId>> getUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<Void> patchUser(UUID id, User body) {
        userService.updateUser(id, body);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> postUser(User body) {
        userService.createUser(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> uploadAvatar(UUID id, MultipartFile receipt) {
        userService.uploadUserAvatar(id, receipt);
        return ResponseEntity.ok().build();
    }
}

