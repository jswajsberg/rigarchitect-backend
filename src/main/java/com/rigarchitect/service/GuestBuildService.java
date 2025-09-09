package com.rigarchitect.service;

import com.rigarchitect.model.GuestBuild;
import com.rigarchitect.model.GuestSession;
import com.rigarchitect.repository.GuestBuildRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing guest build operations including creation, retrieval, and persistence.
 * Handles build data storage for anonymous users through their guest sessions.
 */
@Service
public class GuestBuildService {

    private final GuestBuildRepository guestBuildRepository;
    private final GuestSessionService guestSessionService;

    /**
     * Constructor with required service and repository dependencies.
     */
    public GuestBuildService(GuestBuildRepository guestBuildRepository, GuestSessionService guestSessionService) {
        this.guestBuildRepository = guestBuildRepository;
        this.guestSessionService = guestSessionService;
    }

    /**
     * Creates a new guest build for the specified session.
     *
     * @param sessionId the session ID
     * @param buildData the build configuration as JSON string
     * @return the created guest build
     * @throws IllegalArgumentException if session is not found or invalid
     */
    @Transactional
    public GuestBuild createBuild(String sessionId, String buildData) {
        GuestSession session = guestSessionService.validateAndRefreshSession(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired session: " + sessionId));

        GuestBuild build = new GuestBuild();
        build.setGuestSession(session);
        build.setBuildData(buildData);

        return guestBuildRepository.save(build);
    }

    /**
     * Updates an existing guest build.
     *
     * @param buildId the build ID to update
     * @param buildData the new build configuration as JSON string
     * @return the updated guest build
     * @throws IllegalArgumentException if build is not found
     */
    @Transactional
    public GuestBuild updateBuild(Long buildId, String buildData) {
        GuestBuild build = guestBuildRepository.findById(buildId)
                .orElseThrow(() -> new IllegalArgumentException("Guest build not found: " + buildId));

        // Validate session is still valid
        String sessionId = build.getSessionId();
        guestSessionService.validateAndRefreshSession(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session expired or invalid: " + sessionId));

        build.setBuildData(buildData);
        return guestBuildRepository.save(build);
    }

    /**
     * Gets a guest build by its ID.
     *
     * @param buildId the build ID
     * @return optional guest build
     */
    public Optional<GuestBuild> getBuildById(Long buildId) {
        return guestBuildRepository.findById(buildId);
    }

    /**
     * Gets all builds for a specific session.
     *
     * @param sessionId the session ID
     * @return list of guest builds
     */
    public List<GuestBuild> getBuildsBySessionId(String sessionId) {
        return guestBuildRepository.findBySessionId(sessionId);
    }


    /**
     * Deletes a guest build.
     *
     * @param buildId the build ID to delete
     * @throws IllegalArgumentException if build is not found
     */
    @Transactional
    public void deleteBuild(Long buildId) {
        if (!guestBuildRepository.existsById(buildId)) {
            throw new IllegalArgumentException("Guest build not found: " + buildId);
        }
        guestBuildRepository.deleteById(buildId);
    }

    /**
     * Counts the number of builds for a specific session.
     *
     * @param sessionId the session ID
     * @return number of builds
     */
    public long countBuildsBySessionId(String sessionId) {
        return guestBuildRepository.countBySessionId(sessionId);
    }

    /**
     * Gets the latest build for a specific session.
     *
     * @param sessionId the session ID
     * @return optional latest guest build
     */
    public Optional<GuestBuild> getLatestBuildBySessionId(String sessionId) {
        List<GuestBuild> builds = guestBuildRepository.findBySessionId(sessionId);
        return builds.stream()
                .max(Comparator.comparing(GuestBuild::getUpdatedAt));
    }

}