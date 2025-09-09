package com.rigarchitect.service;

import com.rigarchitect.dto.user.UserRequest;
import com.rigarchitect.dto.user.UserResponse;
import com.rigarchitect.model.User;
import com.rigarchitect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for managing user operations including CRUD and authentication support.
 * Handles user creation, updates, budget management, and DTO conversions.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor with required dependencies.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Gets user entity by ID for internal service use.
     */
    public Optional<User> getUserEntityById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Gets user response DTO by ID for API responses.
     */
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::toResponse);
    }

    /**
     * Gets all users as response DTOs.
     */
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Gets the currently authenticated user from security context.
     */
    public Optional<UserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return getUserById(userPrincipal.getId());
    }



    /**
     * Creates a new user from request data.
     */
    public UserResponse createUser(UserRequest request) {
        User user = toEntity(request);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    /**
     * Updates an existing user with new data.
     */
    public Optional<UserResponse> updateUser(Long id, UserRequest request) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(request.email());
                    existing.setName(request.name());
                    existing.setBudget(request.budget());
                    return toResponse(userRepository.save(existing));
                });
    }

    /**
     * Updates only the user's budget, useful for cart finalization.
     */
    public Optional<UserResponse> updateUserBudget(Long id, BigDecimal newBudget) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setBudget(newBudget);
                    return toResponse(userRepository.save(existing));
                });
    }

    /**
     * Deletes a user by ID.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Finds a user by email address.
     */
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toResponse);
    }


    /**
     * Converts User entity to UserResponse DTO.
     */
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

    /**
     * Converts UserRequest DTO to User entity with temporary password.
     */
    private User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setBudget(request.budget());
        user.setPasswordHash(passwordEncoder.encode("temp_password_" + System.currentTimeMillis()));
        return user;
    }
}