package com.rigarchitect.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a guest session for anonymous users.
 * Maps to the 'guest_sessions' table in the database.
 */
@Entity
@Table(name = "guest_sessions")
@Getter
@Setter
@NoArgsConstructor
public class GuestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "session_id", nullable = false, unique = true)
    private String sessionId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "last_accessed")
    private LocalDateTime lastAccessed;

    @OneToMany(mappedBy = "guestSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("session-builds")
    private List<GuestBuild> guestBuilds;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastAccessed = LocalDateTime.now();
        
        // Initialize guest builds list if null
        if (guestBuilds == null) {
            guestBuilds = new ArrayList<>();
        }
        
        // Sessions expire after 30 days
        if (expiresAt == null) {
            expiresAt = createdAt.plusDays(30);
        }
        
        // Generate session ID if not provided
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastAccessed = LocalDateTime.now();
    }

    /**
     * Checks if the session has expired.
     *
     * @return true if the session is expired, false otherwise
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /**
     * Extends the session expiration by 30 days from now.
     */
    public void extendExpiration() {
        expiresAt = LocalDateTime.now().plusDays(30);
        lastAccessed = LocalDateTime.now();
    }
}