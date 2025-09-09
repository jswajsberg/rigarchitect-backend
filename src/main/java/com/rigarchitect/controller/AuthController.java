package com.rigarchitect.controller;

import com.rigarchitect.config.JwtUtils;
import com.rigarchitect.dto.MessageResponse;
import com.rigarchitect.dto.auth.*;
import com.rigarchitect.dto.user.UserResponse;
import com.rigarchitect.model.User;
import com.rigarchitect.repository.UserRepository;
import com.rigarchitect.service.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * REST controller for JWT-based authentication operations.
 * Handles login, registration, token refresh, and password management.
 */
@Tag(name = "Authentication", description = "JWT-based authentication endpoints")
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Authenticates user and returns JWT tokens.
     */
    @Operation(summary = "Authenticate user", description = "Login with email and password to receive JWT tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            String accessToken = jwtUtils.generateAccessToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication);
            
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            
            long expiresAt = jwtUtils.getExpirationDateFromToken(accessToken).getTime();
            
            UserResponse userResponse = new UserResponse(
                    userPrincipal.getId(),
                    userPrincipal.getName(),
                    userPrincipal.getEmail(),
                    userPrincipal.getBudget()
            );

            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken, expiresAt, userResponse));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Authentication failed: " + e.getMessage()));
        }
    }

    /**
     * Registers a new user and returns JWT tokens for auto-login.
     */
    @Operation(summary = "Register new user", description = "Create a new user account with email and password")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setName(signUpRequest.name());
        user.setEmail(signUpRequest.email());
        user.setPasswordHash(encoder.encode(signUpRequest.password()));
        
        BigDecimal budget = signUpRequest.budget() != null ? signUpRequest.budget() : new BigDecimal("5000.00");
        user.setBudget(budget);

        User savedUser = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.email(), signUpRequest.password())
        );

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);
        long expiresAt = jwtUtils.getExpirationDateFromToken(accessToken).getTime();

        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getBudget()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JwtResponse(accessToken, refreshToken, expiresAt, userResponse));
    }

    /**
     * Refreshes access token using a valid refresh token.
     */
    @Operation(summary = "Refresh access token", description = "Use refresh token to obtain a new access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        try {
            if (jwtUtils.validateJwtToken(refreshToken)) {
                String email = jwtUtils.getUserNameFromJwtToken(refreshToken);
                
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                UserPrincipal userPrincipal = UserPrincipal.build(user);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userPrincipal, null, userPrincipal.getAuthorities());

                String newAccessToken = jwtUtils.generateAccessToken(auth);
                long expiresAt = jwtUtils.getExpirationDateFromToken(newAccessToken).getTime();

                UserResponse userResponse = new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getBudget()
                );

                return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken, expiresAt, userResponse));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Invalid refresh token"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Token refresh failed: " + e.getMessage()));
        }
    }

    /**
     * Logs out user by clearing security context.
     */
    @Operation(summary = "Logout user", description = "Logout current user (client should discard tokens)")
    @ApiResponse(responseCode = "200", description = "Logout successful")
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("Logout successful"));
    }

    /**
     * Verifies JWT token validity and returns user information.
     */
    @Operation(summary = "Verify token", description = "Verify if the current JWT token is valid")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired token")
    })
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            
            UserResponse userResponse = new UserResponse(
                    userPrincipal.getId(),
                    userPrincipal.getName(),
                    userPrincipal.getEmail(),
                    userPrincipal.getBudget()
            );
            
            return ResponseEntity.ok(userResponse);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Invalid token"));
    }

    /**
     * Changes password for the authenticated user.
     */
    @Operation(summary = "Change password", description = "Change password for authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid current password"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Not authenticated"));
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            
            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!encoder.matches(request.currentPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Current password is incorrect"));
            }

            user.setPasswordHash(encoder.encode(request.newPassword()));
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Password changed successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to change password: " + e.getMessage()));
        }
    }
}