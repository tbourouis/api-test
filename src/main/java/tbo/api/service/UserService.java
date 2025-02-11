package tbo.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tbo.api.entity.mapper.EntityMapper;
import tbo.api.exception.ResourceNotFoundException;
import tbo.api.model.mapper.UserMapper;
import tbo.api.model.UserModel;
import tbo.api.repository.UserRepository;
import tn.tbo.demo.api.v1.model.User;
import tn.tbo.demo.api.v1.model.UserWithId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserWithId getUserById(UUID id) {
        UserModel userModel = userRepository.findById(id)
                .map(EntityMapper.INSTANCE::map)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return UserMapper.INSTANCE.mapToDto(userModel);
    }

    public void deleteUserById(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public List<UserWithId> getAllUsers() {
        var users  = userRepository.findAll();
        return users.stream()
                .map(EntityMapper.INSTANCE::map)
                .map(UserMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    public void createUser(User body) {
        UserModel userModel = UserMapper.INSTANCE.map(body);
        userRepository.save(EntityMapper.INSTANCE.mapToEntity(userModel));
    }

    public void uploadUserAvatar(UUID id, MultipartFile receipt) {
        UserModel userModel = userRepository.findById(id)
                .map(EntityMapper.INSTANCE::map)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        String avatarUrl = "path/to/avatar";

        userModel.setAvatar(avatarUrl);
        userRepository.save(EntityMapper.INSTANCE.mapToEntity(userModel));
    }

    public void updateUser(UUID id, User body) {
        UserModel userModel = userRepository.findById(id)
                .map(EntityMapper.INSTANCE::map)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userModel.toBuilder()
                .username(body.getLogin())
                .password(body.getPassword())
                .build();


        userRepository.save(EntityMapper.INSTANCE.mapToEntity(userModel));
    }
}
