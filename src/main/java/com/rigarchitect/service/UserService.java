package com.rigarchitect.service;

import com.rigarchitect.dto.user.UserRequest;
import com.rigarchitect.dto.user.UserResponse;
import com.rigarchitect.model.User;
import com.rigarchitect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // For controller/service internal use
    public Optional<User> getUserEntityById(Long id) {
        return userRepository.findById(id);
    }

    // For API responses
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::toResponse);
    }

    public UserResponse createUser(UserRequest request) {
        User user = toEntity(request);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public Optional<UserResponse> updateUser(Long id, UserRequest request) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(request.email());
                    existing.setName(request.name());
                    // add other updatable fields
                    return toResponse(userRepository.save(existing));
                });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // --- DTO ↔ Entity mapping methods ---

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBudget(),
                user.getUpdatedAt(),
                user.getCreatedAt()
        );
    }

    private User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setBudget(request.budget());
        return user;
    }
}
