package com.rigarchitect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * Entity class representing a guest user's PC build data.
 * Maps to the 'guest_builds' table in the database.
 */
@Entity
@Table(name = "guest_builds")
@Getter
@Setter
@NoArgsConstructor
public class GuestBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", nullable = false)
    @JsonBackReference("session-builds")
    private GuestSession guestSession;

    @Column(name = "build_data", nullable = false, columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private String buildData;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Convenience method to get the session ID.
     *
     * @return the session ID if guestSession is set, null otherwise
     */
    public String getSessionId() {
        return guestSession != null ? guestSession.getSessionId() : null;
    }
}