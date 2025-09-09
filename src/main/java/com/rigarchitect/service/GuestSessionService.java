package com.rigarchitect.service;

import com.rigarchitect.model.GuestSession;
import com.rigarchitect.repository.GuestBuildRepository;
import com.rigarchitect.repository.GuestSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service for managing guest session operations including creation, validation, and cleanup.
 * Handles session persistence, expiration management, and automatic cleanup of expired sessions.
 */
@Service
public class GuestSessionService {

    private final GuestSessionRepository guestSessionRepository;
    private final GuestBuildRepository guestBuildRepository;

    /**
     * Constructor with required repository dependencies.
     */
    public GuestSessionService(GuestSessionRepository guestSessionRepository, GuestBuildRepository guestBuildRepository) {
        this.guestSessionRepository = guestSessionRepository;
        this.guestBuildRepository = guestBuildRepository;
    }

    /**
     * Creates or retrieves an existing guest session.
     *
     * @param sessionId the session ID to create or retrieve
     * @return the guest session
     */
    @Transactional
    public GuestSession createOrGetSession(String sessionId) {
        Optional<GuestSession> existing = guestSessionRepository.findBySessionId(sessionId);
        
        if (existing.isPresent()) {
            GuestSession session = existing.get();
            
            // Extend expiration if session is about to expire (less than 7 days left)
            if (session.getExpiresAt().isBefore(LocalDateTime.now().plusDays(7))) {
                session.extendExpiration();
                return guestSessionRepository.save(session);
            }
            
            // Update last accessed time
            session.setLastAccessed(LocalDateTime.now());
            return guestSessionRepository.save(session);
        }
        
        // Create new session
        GuestSession newSession = new GuestSession();
        newSession.setSessionId(sessionId);
        return guestSessionRepository.save(newSession);
    }



    /**
     * Validates and refreshes a guest session.
     *
     * @param sessionId the session ID to validate
     * @return the refreshed session if valid, empty if expired or not found
     */
    @Transactional
    public Optional<GuestSession> validateAndRefreshSession(String sessionId) {
        Optional<GuestSession> sessionOpt = guestSessionRepository.findBySessionId(sessionId);
        
        if (sessionOpt.isEmpty()) {
            return Optional.empty();
        }
        
        GuestSession session = sessionOpt.get();
        
        // Check if expired
        if (session.isExpired()) {
            guestSessionRepository.delete(session);
            return Optional.empty();
        }
        
        // Update last accessed time
        session.setLastAccessed(LocalDateTime.now());
        return Optional.of(guestSessionRepository.save(session));
    }

    /**
     * Extends the expiration time of a guest session.
     *
     * @param sessionId the session ID to extend
     * @return the updated session if found, empty otherwise
     */
    @Transactional
    public Optional<GuestSession> extendSession(String sessionId) {
        return guestSessionRepository.findBySessionId(sessionId)
                .map(session -> {
                    session.extendExpiration();
                    return guestSessionRepository.save(session);
                });
    }

    /**
     * Deletes a guest session and all associated data.
     *
     * @param sessionId the session ID to delete
     */
    @Transactional
    public void deleteSession(String sessionId) {
        guestSessionRepository.findBySessionId(sessionId)
                .ifPresent(guestSessionRepository::delete);
    }

    /**
     * Checks if a session ID exists.
     *
     * @param sessionId the session ID to check
     * @return true if exists, false otherwise
     */
    public boolean sessionExists(String sessionId) {
        return guestSessionRepository.existsBySessionId(sessionId);
    }

    /**
     * Scheduled task to clean up expired sessions.
     * Runs every hour to remove expired sessions and their associated data.
     */
    @Scheduled(cron = "0 0 * * * ?") // Every hour at minute 0
    @Transactional
    public void cleanupExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        
        // First clean up expired builds, then sessions
        int deletedBuilds = guestBuildRepository.deleteBuildsWithExpiredSessions(now);
        int deletedSessions = guestSessionRepository.deleteExpiredSessions(now);
        
        if (deletedBuilds > 0 || deletedSessions > 0) {
            System.out.println("Cleaned up " + deletedBuilds + " expired guest builds and " + deletedSessions + " expired guest sessions");
        }
    }
}