package com.rigarchitect.service;

import com.rigarchitect.dto.user.UserRequest;
import com.rigarchitect.dto.user.UserResponse;
import com.rigarchitect.model.User;
import com.rigarchitect.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // For controller/service internal use
    public Optional<User> getUserEntityById(Long id) {
        return userRepository.findById(id);
    }

    // For API responses
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::toResponse);
    }

    // NEW: Get all users for selection
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // NEW: Get the current user (hardcoded to first user for now, easily replaceable with auth later)
    public Optional<UserResponse> getCurrentUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return Optional.empty();
        }
        // For now, return the first user. This will be replaced with authenticated user later
        return Optional.of(toResponse(users.get(0)));
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
                    existing.setBudget(request.budget());
                    return toResponse(userRepository.save(existing));
                });
    }

    // NEW: Update just the budget (useful for cart operations)
    public Optional<UserResponse> updateUserBudget(Long id, BigDecimal newBudget) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setBudget(newBudget);
                    return toResponse(userRepository.save(existing));
                });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // NEW: Find user by email (useful for user lookup)
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toResponse);
    }

    // --- DTO ↔ Entity mapping methods ---

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBudget(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setBudget(request.budget());
        // Set a default password hash for users created via UserRequest (admin/system users)
        // These users will need to reset their password to log in
        user.setPasswordHash(passwordEncoder.encode("temp_password_" + System.currentTimeMillis()));
        return user;
    }
}