package com.rigarchitect.repository;

import com.rigarchitect.model.GuestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for GuestSession entities.
 * Provides data access methods for guest session operations.
 */
public interface GuestSessionRepository extends JpaRepository<GuestSession, UUID> {
    
    /**
     * Finds a guest session by its session ID.
     *
     * @param sessionId the session ID to search for
     * @return optional guest session
     */
    Optional<GuestSession> findBySessionId(String sessionId);
    
    /**
     * Checks if a session ID exists in the database.
     *
     * @param sessionId the session ID to check
     * @return true if exists, false otherwise
     */
    boolean existsBySessionId(String sessionId);
    
    /**
     * Deletes all expired guest sessions.
     *
     * @param now the current timestamp
     * @return number of deleted sessions
     */
    @Modifying
    @Query("DELETE FROM GuestSession gs WHERE gs.expiresAt < :now")
    int deleteExpiredSessions(@Param("now") LocalDateTime now);
    
}