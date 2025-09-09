package com.rigarchitect.repository;

import com.rigarchitect.model.GuestBuild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for GuestBuild entities.
 * Provides data access methods for guest build operations.
 */
public interface GuestBuildRepository extends JpaRepository<GuestBuild, Long> {
    
    
    /**
     * Finds all guest builds by session ID.
     *
     * @param sessionId the session ID
     * @return list of guest builds
     */
    @Query("SELECT gb FROM GuestBuild gb WHERE gb.guestSession.sessionId = :sessionId")
    List<GuestBuild> findBySessionId(@Param("sessionId") String sessionId);
    
    /**
     * Deletes all builds associated with expired sessions.
     *
     * @param now the current timestamp
     * @return number of deleted builds
     */
    @Modifying
    @Query("DELETE FROM GuestBuild gb WHERE gb.guestSession.expiresAt < :now")
    int deleteBuildsWithExpiredSessions(@Param("now") LocalDateTime now);
    
    /**
     * Counts the number of builds for a specific session.
     *
     * @param sessionId the session ID
     * @return number of builds
     */
    @Query("SELECT COUNT(gb) FROM GuestBuild gb WHERE gb.guestSession.sessionId = :sessionId")
    long countBySessionId(@Param("sessionId") String sessionId);
}